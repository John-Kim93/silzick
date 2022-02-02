import { apiInstance } from "./index.js";

const api = apiInstance();

function getRequests (success, fail) {
  api.get('/board',{
  },).then(success).catch(fail);
}

export { getRequests }