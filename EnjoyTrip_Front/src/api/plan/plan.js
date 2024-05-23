import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function registTripPlan(param, success, fail) {
  console.log('파람');
  console.dir(param);
  await local.post(`/plan`, param).then(success).catch(fail);
}

async function editTripPlan(param, success, fail) {
  console.log('업데이트파람');
  console.dir(param);
  await local.put(`/plan`, param).then(success).catch(fail);
}

async function getListMyTripPlan(memberId, success, fail) {
  await local.get(`/plan/myplan/${memberId}`).then(success).catch(fail);
}

async function getDetailTripPlan(planId, success, fail) {
  await local.get(`/plan/planid/${planId}`).then(success).catch(fail);
}

async function getDetailMakeTripPlan(param, success, fail) {
  console.dir('dto파람');
  console.dir(param);
  await local
    .get(`/plan/maketripplan`, { params: param })
    .then(success)
    .catch(fail);
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
