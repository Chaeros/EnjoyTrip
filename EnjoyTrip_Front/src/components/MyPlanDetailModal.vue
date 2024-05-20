<script setup>
import { ref, defineProps, defineEmits } from 'vue';

import { watch } from 'vue';
import AttractionSearchBar from '@/components/AttractionSearchBar.vue';
import AccomodationSearchBar from '@/components/AccomodationSearchBar.vue';
import KakaoMap from '@/components/KakaoMap.vue';
import AttractionSearch from '@/components/AttractionSearch.vue';
import AccomodationSearch from '@/components/AccomodationSearch.vue';
import DateCheck from '@/components/DateCheck.vue';
import TotalSelectedAttractions from '@/components/TotalSelectedAttractions.vue';
import DateContainer from '@/components/DateContainer.vue';
import AttractionAddModal from '@/components/AttractionAddModal.vue';
import AccomodationAddModal from '@/components/AccomodationAddModal.vue';
import ShowPlanDetailModal from '@/components/ShowPlanDetailModal.vue';
import fillDetailPlanModal from '@/components/FillDetailPlanModal.vue';
import MyPlanListModal from '@/components/MyPlanListModal.vue';

const { tripPlanId } = defineProps({
  tripPlanId: Number,
});
const emit = defineEmits(['clickAttractionAdd']);

const clickAttractionAdd = () => {
  emit('clickAttractionAdd', attraction);
};
</script>

<template>
  <div class="all-content-plan">
    <div class="left-tab-plan">
      <header id="header" class="plan-header">
        <div class="logo-nav-plan">
          <img src="@/img/coldragon.png" class="coldragon-img-plan" />
          <nav class="select-group-plan">
            <a
              href="#"
              class="list-group-item list-group-item-action"
              :class="{ active: activeDate === 0 }"
              @click.prevent="setActiveDate(0)"
              >전체 일정</a
            >
            <a
              href="#"
              v-for="number in totalTripDates"
              class="list-group-item list-group-item-action"
              :class="{ active: activeDate === number }"
              @click.prevent="setActiveDate(number)"
              >{{ number }}일차</a
            >
          </nav>
        </div>
        <div class="make-plan">
          <button
            type="button"
            id="save-btn"
            class="btn btn-dark"
            style="
              --bs-btn-padding-y: 1rem;
              --bs-btn-padding-x: 0.25rem;
              --bs-btn-font-size: 1.1rem;
            "
            @click="showPlanDetailModalToggle"
          >
            저장하기
          </button>
        </div>
      </header>

      <div class="place-save">
        <TotalSelectedAttractions
          @attraction-add-modal-toggle="attractionAddModalToggle"
          @dragged="handleDragged"
          :selected-attractions="selectedAttractions"
          :selected-accomodations="selectedAccomodations"
        />
      </div>
    </div>
  </div>

  <div
    class="date-container-container"
    v-if="currentView === 'plan'"
    :style="{ maxWidth: dateContainerMaxWidth }"
  >
    <DateContainer
      v-for="number in totalTripDates"
      v-show="activeDate === 0 || activeDate === number"
      :selected-attractions-by-date="selectedAttractionsByDate"
      :date="number"
      @drop="onDrop($event, number)"
      @dragenter.prevent
      @dragover.prevent
      @date-container-dragged="dateContainerDragged"
      @remove-attraction="removeAttraction"
      @on-date-drop="onDateDrop"
      @on-between-drop="onBetweenDrop"
      @detail-plan-modal-open="detailPlanModalOpen"
    ></DateContainer>
  </div>

  <div class="plan-buttons">
    <button type="button" @click.prevent="OpenMyPlanListModal">내 계획</button>
    <button type="button">댓글</button>
    <button type="button">공유</button>
  </div>
</template>

<style scoped></style>
