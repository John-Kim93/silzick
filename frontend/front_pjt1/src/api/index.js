import axios from "axios"
import { API_BASE_URL, API_OPENVIDU_URL, OPENVIDU_SERVER_SECRET } from "@/config"

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
    withCredentials: true,
  });
  return instance
}

function openviduApiInstance () {
  const instance = axios.create({
    baseURL: API_OPENVIDU_URL,
    headers: {
      "Content-type": "application/json",
    },
    auth
  })
}

export { apiInstance }