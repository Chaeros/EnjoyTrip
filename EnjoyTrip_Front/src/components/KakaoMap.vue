<script setup>
import { ref, onMounted } from 'vue';
import { mapKey } from '../config/index.ts';
import { defineExpose, watch } from 'vue';

// selectedAttractions은 잘 받아옴.

const { selectedAttractions, selectedAccomodations } = defineProps({
  selectedAttractions: Object,
  selectedAccomodations: Object,
});
let map = ref(null);

onMounted(() => {
  console.dir('숙소 조합');
  console.dir(selectedAccomodations);
  if (window.kakao && window.kakao.maps) {
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
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(36.35537731926109, 127.29847072801634),
    level: 5,
  };

  // 지도 객체를 등록합니다.
  // 지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
  map.value = new kakao.maps.Map(container, options);
};

/*
let container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스

let options = {
  //지도를 생성할 때 필요한 기본 옵션
  center: new kakao.maps.LatLng(36.35537731926109, 127.29847072801634), //지도의 중심좌표.
  level: 4, //지도의 레벨(확대, 축소 정도)
};

let overlays = [];
let markers = [];
let map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 장소 검색 객체를 생성합니다
let ps = new kakao.maps.services.Places();

let mapCenterX;
let mapCenterY;

let positions = [];
let contentTypeId;
let searchA;
*/

const markers = ref([]);
const overlays = ref([]);

const attractionMarkers = ref([]);
const attractionOverlays = ref([]);

const accomodationMarkers = ref([]);
const accomodationOverlays = ref([]);

const updateMapLocation = () => {
  clearMarkersAndOverlays();
  makeMarkersAndOverlays();
  toggleOverlays();
};

const clearMarkersAndOverlays = () => {
  for (let i = 0; i < markers.value.length; i++) {
    markers.value[i].setMap(null);
    overlays.value[i].setMap(null);
  }

  markers.value = [];
  overlays.value = [];
};

const makeMarkersAndOverlays = () => {
  const positions = ref([]);
  selectedAttractions.forEach((attraction) => {
    const newCenter = new kakao.maps.LatLng(
      attraction.attractInfo.latitude,
      attraction.attractInfo.longitude
    );
    map.value.setCenter(newCenter);
    const marker = new kakao.maps.Marker({
      map: map.value,
      position: newCenter,
    });
    markers.value.push(marker);

    const content = getOverlayContent(attraction);

    const overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: map.value,
      position: marker.getPosition(),
    });

    overlays.value.push(overlay);
  });
};

const fillOverlays = () => {
  console.dir(overlays.value);
  console.dir(overlays.value.length);
  for (let i = 0; i < selectedAttractions.length; i++) {
    const attraction = selectedAttractions[i];
    overlays.value[i].content = getOverlayContent(attraction);
  }
};

const getOverlayContent = (attraction) => {
  return (
    '<div class="overlay_wrap">' +
    '    <div class="overlay_info">' +
    '        <div class="overlay_title">' +
    `            ${attraction.attractInfo.title}` +
    // `            <div class="overlay_close" onclick="closeOverlay(${i})" title="닫기"></div>` +
    '        </div>' +
    '        <div class="overlay_body">' +
    '            <div class="overlay_img">' +
    `               <img src="${attraction.attractInfo.firstImage}" width="73" height="70">` +
    '           </div>' +
    '            <div class="overlay_desc">' +
    `                <div class="overlay_ellipsis">${attraction.attractInfo.addr1}</div>` +
    `                <div class="overlay_jibun overlay_ellipsis">${attraction.attractInfo.addr2}</div>` +
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
    // `<style>` +
    // `.overlay_wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun +
    // Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}``.overlay_wrap * {padding: 0;margin: 0;}` +
    // `.overlay_wrap .overlay_info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}` +
    // `.overlay_wrap .overlay_info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}` +
    // `.overlay_info .overlay_title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}` +
    // `.overlay_info .overlay_close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/
    // overlay_close.png');}` +
    // `.overlay_info .overlay_close:hover {cursor: pointer;}` +
    // `.overlay_info .overlay_body {position: relative;overflow: hidden;}` +
    // `.overlay_info .overlay_desc {position: relative;margin: 13px 0 0 90px;height: 75px;}` +
    // `.overlay_desc .overlay_ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}` +
    // `.overlay_desc .overlay_jibun {font-size: 11px;color: #888;margin-top: -2px;}` +
    // `.overlay_info .overlay_img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}` +
    // `.overlay_info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}` +
    // `.overlay_info .overlay_link {color: #5085BB;}` +
    // `</style>`
  );
};

const toggleOverlays = () => {
  for (let i = 0; i < markers.value.length; i++) {
    const marker = markers.value[i];
    kakao.maps.event.addListener(marker, 'click', function () {
      if (overlays.value[i].getMap() === null) {
        console.dir('null임.');
        console.dir('mapValue');
        console.dir(map.value);
        overlays.value[i].setMap(map.value);
      } else {
        overlays.value[i].setMap(null);
      }
    });
  }
};

// updateMapLocation() {
//     for (let i=0; i<selectedAttractions.length; i++) {
//     const attraction = selectedAttractions[i];
//     console.dir(attraction);

//     const newCenter = new kakao.maps.LatLng(attraction.attractInfo.latitude, attraction.attractInfo.longitude);
//     map.value.setCenter(newCenter);
//     const marker = new kakao.maps.Marker({
//       position: newCenter
//     });
//     marker.setMap(map.value);
//     markers.value.push(marker);

// const content =
//    '<div class="wrap">' +
//    '    <div class="info">' +
//    '        <div class="title">' +
//    `            ${attraction.attractInfo.title}` +
//    `            <div class="close" onclick="closeOverlay(${i})" title="닫기"></div>` +
//    "        </div>" +
//    '        <div class="body">' +
//    '            <div class="img">' +
//    `                <img src=${
//      attraction.attractInfo.firstimage
//        ? attraction.attractInfo.firstimage
//        : "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png"
//    } width="73" height="70">` +
//    "           </div>" +
//    '            <div class="desc">' +
//    `                <div class="ellipsis">${attraction.attractInfo.addr1}</div>` +
//    `                <div class="jibun ellipsis">${attraction.attractInfo.addr2}</div>` +
//    `                <div><a href="https://map.kakao.com/link/roadview/${attraction.attractInfo.mapy},${attraction.attractInfo.mapx}" target="_blank" class="link">로드뷰 보기</a></div>` +
//    "            </div>" +
//    "        </div>" +
//    "    </div>" +
//    "</div>";

//     var overlay = new kakao.maps.CustomOverlay({
//       content: content,
//       map: map,
//       position: marker.getPosition()
//     });

//     overlays.push(overlay);

//     // const content =
//     //   '<div class="wrap">' +
//     //   '    <div class="info">' +
//     //   '        <div class="title">' +
//     //   `            ${attraction.attractInfo.title}` +
//     //   `            <div class="close" onclick="closeOverlay(${i})" title="닫기"></div>` +
//     //   "        </div>" +
//     //   '        <div class="body">' +
//     //   '            <div class="img">' +
//     //   `                <img src=${
//     //     attraction.attractInfo.firstimage
//     //       ? attraction.attractInfo.firstimage
//     //       : "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png"
//     //   } width="73" height="70">` +
//     //   "           </div>" +
//     //   '            <div class="desc">' +
//     //   `                <div class="ellipsis">${attraction.attractInfo.addr1}</div>` +
//     //   `                <div class="jibun ellipsis">${attraction.attractInfo.addr2}</div>` +
//     //   `                <div><a href="https://map.kakao.com/link/roadview/${attraction.attractInfo.mapy},${attraction.attractInfo.mapx}" target="_blank" class="link">로드뷰 보기</a></div>` +
//     //   "            </div>" +
//     //   "        </div>" +
//     //   "    </div>" +
//     //   "</div>";

//     // var overlay = new kakao.maps.CustomOverlay({
//     //   content: content,
//     //   map: map,
//     //   position: marker.getPosition()
//     // });

//     // overlays.push(overlay);
//     // // // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
//     // // kakao.maps.event.addListener(marker, "click", function () {
//     // //   if (overlay.getMap() === null) {
//     // //     overlay.setMap(map);
//     // //   } else {
//     // //     overlay.setMap(null);
//     // //   }
//     // // });

//     }

//     for (let i=0; i<markers.length; i++) {
//       let marker = markers[i];
//       kakao.maps.event.addListener(marker, "click", function () {
//       console.log(markers[i].getMap());
//       if (overlays[i].getMap() === null) {
//         overlays[i].setMap(map);
//       } else {
//         overlays[i].setMap(null);
//       }
//     });
//     }

//   }

/*
// 주변 마커 세팅
function makeMarker(items) {
  console.dir(items);
  positions = [];
  items.forEach((item) => {
    let param = {
      content: item.title,
      latlng: new kakao.maps.LatLng(item.mapy, item.mapx),
    };
    positions.push(param);
  });
  for (let i = 0; i < positions.length; i++) {
    // 마커를 생성합니다
    let marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng,
    });
    markers.push(marker);

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다

    // 커스텀 오버레이에 표시할 컨텐츠 입니다
    // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
    // 별도의 이벤트 메소드를 제공하지 않습니다
    let content =
      '<div class="wrap">' +
      '    <div class="info">' +
      '        <div class="title">' +
      `            ${items[i].title}` +
      `            <div class="close" onclick="closeOverlay(${i})" title="닫기"></div>` +
      "        </div>" +
      '        <div class="body">' +
      '            <div class="img">' +
      `                <img src=${
        items[i].firstimage
          ? items[i].firstimage
          : "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png"
      } width="73" height="70">` +
      "           </div>" +
      '            <div class="desc">' +
      `                <div class="ellipsis">${items[i].addr1}</div>` +
      `                <div class="jibun ellipsis">${items[i].addr2}</div>` +
      `                <div><a href="https://map.kakao.com/link/roadview/${items[i].mapy},${items[i].mapx}" target="_blank" class="link">로드뷰 보기</a></div>` +
      "            </div>" +
      "        </div>" +
      "    </div>" +
      "</div>";

    let overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: map,
      position: positions[i].latlng,
    });

    overlays.push(overlay);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, "click", function () {
      console.log(markers[i].getMap());
      if (overlays[i].getMap() === null) {
        overlays[i].setMap(map);
      } else {
        overlays[i].setMap(null);
      }
    });
  }
  console.log(2);

  // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
  // LatLngBounds 객체에 좌표를 추가합니다
  var bounds = new kakao.maps.LatLngBounds();

  for (var i = 0; i < items.length; i++) {
    console.log(items[i]);
    bounds.extend(new kakao.maps.LatLng(items[i].mapy, items[i].mapx));
  }

  // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
  map.setBounds(bounds);
}

// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
function closeOverlay(idx) {
  overlays[idx].setMap(null);
}
*/

const selectedAttractionsWatch = watch(
  selectedAttractions,
  (newValue, oldValue) => {
    updateMapLocation();
  }
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
