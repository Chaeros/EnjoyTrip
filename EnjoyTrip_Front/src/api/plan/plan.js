import { localAxios } from '@/util/http-commons';

const local = localAxios();

function registTripPlan(param, success, fail) {
  console.log('파람');
  console.dir(param);
  local.post(`/plan`, param).then(success).catch(fail);
}

export { registTripPlan };
