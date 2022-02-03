import { apiInstance } from "./index.js";

const api = apiInstance();

async function login (user, success, fail) {
  await api.post('/user/login', JSON.stringify(user),{
    withCredentials: true,
  },).then(success).catch(fail);
}

function signup (user, success, fail) {
 api.post('/user/signup', JSON.stringify(user)).then(success).catch(fail);
}

export { login, signup, }
