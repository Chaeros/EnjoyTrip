import { localAxios } from '@/util/http-commons';

const local = localAxios();

function registTripPlan(param, success, fail) {
  console.log('파람');
  console.dir(param);
  local.post(`/plan`, param).then(success).catch(fail);
}

function editTripPlan(param, success, fail) {
  console.log('업데이트파람');
  console.dir(param);
  local.put(`/plan`, param).then(success).catch(fail);
}

function getListMyTripPlan(memberId, success, fail) {
  local.get(`/plan/myplan/${memberId}`).then(success).catch(fail);
}

function getDetailTripPlan(planId, success, fail) {
  local.get(`/plan/planid/${planId}`).then(success).catch(fail);
}

function getDetailMakeTripPlan(param, success, fail) {
  console.dir('dto파람');
  console.dir(param);
  local.get(`/plan/maketripplan`, { params: param }).then(success).catch(fail);
}

async function removeTripPlan(planId, success, fail) {
  console.dir('planId');
  console.dir(planId);
  await local.delete(`/plan/${planId}`).then(success).catch(fail);
}

export {
  registTripPlan,
  editTripPlan,
  getListMyTripPlan,
  getDetailTripPlan,
  getDetailMakeTripPlan,
  removeTripPlan,
};
