<script setup>
import { ref, onMounted, watch } from 'vue';
import { mapKey } from '../config/index.ts';

const { selectedAttractions, selectedAccomodations } = defineProps({
  selectedAttractions: Array,
  selectedAccomodations: Array,
});
console.dir('셀렉트');
console.dir(selectedAttractions);
console.dir(selectedAccomodations);
let map = ref(null);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    console.dir('카카오 잘됨');
    initMap();
  } else {
    const script = document.createElement('script');
    /* global kakao */
    script.onload = () => kakao.maps.load(initMap);
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${mapKey}`;
    document.head.appendChild(script);
  }
});

const initMap = () => {
  console.dir('initmap 호출');
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(36.35537731926109, 127.29847072801634),
    level: 5,
  };

  // 지도 객체를 등록합니다.
  // 지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
  map.value = new kakao.maps.Map(container, options);

  if (
    typeof selectedAttractions.value === 'undefined' ||
    typeof selectedAccomodations.value === 'undefined' ||
    selectedAttractions.value.length === 0 ||
    selectedAccomodations.value.length === 0
  ) {
    console.log('ㅂㅂ');
    console.log(typeof selectedAttractions.value);
    console.log(selectedAccomodations.value);
    return;
  }

  updateMapLocation();
};

const attractionMarkers = ref([]);
const attractionOverlays = ref([]);

const accomodationMarkers = ref([]);
const accomodationOverlays = ref([]);

const updateMapLocation = () => {
  clearMarkersAndOverlays();
  makeMarkersAndOverlays();
  adjustMapToBounds();
  toggleOverlays();
};

const clearMarkersAndOverlays = () => {
  for (let i = 0; i < attractionMarkers.value.length; i++) {
    attractionMarkers.value[i].setMap(null);
    attractionOverlays.value[i].setMap(null);
  }

  for (let i = 0; i < accomodationMarkers.value.length; i++) {
    accomodationMarkers.value[i].setMap(null);
    accomodationOverlays.value[i].setMap(null);
  }

  attractionMarkers.value = [];
  attractionOverlays.value = [];
  accomodationMarkers.value = [];
  accomodationOverlays.value = [];
};

const makeMarkersAndOverlays = () => {
  selectedAttractions.forEach((attraction) => {
    const newCenter = new kakao.maps.LatLng(
      attraction.attractionInfo.latitude,
      attraction.attractionInfo.longitude
    );
    map.value.setCenter(newCenter);
    const marker = new kakao.maps.Marker({
      map: map.value,
      position: newCenter,
    });
    attractionMarkers.value.push(marker);

    const content = getOverlayContent(attraction);

    const overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: map.value,
      position: marker.getPosition(),
    });

    attractionOverlays.value.push(overlay);
  });

  selectedAccomodations.forEach((attraction) => {
    const newCenter = new kakao.maps.LatLng(
      attraction.attractionInfo.latitude,
      attraction.attractionInfo.longitude
    );
    map.value.setCenter(newCenter);

    const imageSrc = new URL(
        './img/red_marker_2.png',
        'http://localhost:5173/src/'
      ).href, // 마커이미지의 주소입니다
      imageSize = new kakao.maps.Size(39, 42), // 마커이미지의 크기입니다
      imageOption = { offset: new kakao.maps.Point(20, 39) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

    console.dir(imageSrc);
    // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
    const markerImage = new kakao.maps.MarkerImage(
      imageSrc,
      imageSize,
      imageOption
    );

    const marker = new kakao.maps.Marker({
      map: map.value,
      position: newCenter,
      image: markerImage,
    });
    accomodationMarkers.value.push(marker);

    const content = getOverlayContent(attraction);

    const overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: map.value,
      position: marker.getPosition(),
    });

    accomodationOverlays.value.push(overlay);
  });
};

const getOverlayContent = (attraction) => {
  return (
    '<div class="overlay_wrap">' +
    '    <div class="overlay_info">' +
    '        <div class="overlay_title">' +
    `            ${attraction.attractionInfo.title}` +
    // `            <div class="overlay_close" onclick="closeOverlay(${i})" title="닫기"></div>` +
    '        </div>' +
    '        <div class="overlay_body">' +
    '            <div class="overlay_img">' +
    `               <img src="${attraction.attractionInfo.firstImage}" width="73" height="70">` +
    '           </div>' +
    '            <div class="overlay_desc">' +
    `                <div class="overlay_ellipsis">${attraction.attractionInfo.addr1}</div>` +
    `                <div class="overlay_jibun overlay_ellipsis">${attraction.attractionInfo.addr2}</div>` +
    '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="overlay_link">홈페이지</a></div>' +
    '            </div>' +
    '        </div>' +
    '    </div>' +
    '</div>' +
    '<style>' +
    `    .overlay_wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}` +
    `    .overlay_wrap * {padding: 0;margin: 0;}` +
    `    .overlay_wrap .overlay_info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}` +
    `    .overlay_wrap .overlay_info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}` +
    `    .overlay_info .overlay_title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}` +
    `    .overlay_info .overlay_close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}` +
    `    .overlay_info .overlay_close:hover {cursor: pointer;}` +
    `    .overlay_info .overlay_body {position: relative;overflow: hidden;}` +
    `    .overlay_info .overlay_desc {position: relative;margin: 13px 0 0 90px;height: 75px;}` +
    `    .overlay_desc .overlay_ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}` +
    `    .overlay_desc .overlay_jibun {font-size: 11px;color: #888;margin-top: -2px;}` +
    `    .overlay_info .overlay_img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}` +
    `    .overlay_info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}` +
    `    .overlay_info .overlay_link {color: #5085BB;}` +
    '</style>'
  );
};

const adjustMapToBounds = () => {
  const bounds = new kakao.maps.LatLngBounds();

  attractionMarkers.value.forEach((marker) => {
    bounds.extend(marker.getPosition());
  });

  accomodationMarkers.value.forEach((marker) => {
    bounds.extend(marker.getPosition());
  });

  map.value.setBounds(bounds);
};

const toggleOverlays = () => {
  for (let i = 0; i < attractionMarkers.value.length; i++) {
    const marker = attractionMarkers.value[i];
    kakao.maps.event.addListener(marker, 'click', function () {
      if (attractionOverlays.value[i].getMap() === null) {
        console.dir(map.value);
        attractionOverlays.value[i].setMap(map.value);
      } else {
        attractionOverlays.value[i].setMap(null);
      }
    });
  }

  for (let i = 0; i < accomodationMarkers.value.length; i++) {
    const marker = accomodationMarkers.value[i];
    kakao.maps.event.addListener(marker, 'click', function () {
      if (accomodationOverlays.value[i].getMap() === null) {
        console.dir(map.value);
        accomodationOverlays.value[i].setMap(map.value);
      } else {
        accomodationOverlays.value[i].setMap(null);
      }
    });
  }
};

const selectedAttractionsWatch = watch(
  selectedAttractions,
  (newValue, oldValue) => {
    updateMapLocation();
  },
  { deep: true }
);

const selectedAccomodationsWatch = watch(
  selectedAccomodations,
  (newValue, oldValue) => {
    updateMapLocation();
  },
  { deep: true }
);
</script>

<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 100%;
  height: 100%;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: '';
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png');
}
.info .link {
  color: #5085bb;
}
</style>
