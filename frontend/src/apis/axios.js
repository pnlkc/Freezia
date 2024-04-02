import axios from 'axios';

const client = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
});

client.interceptors.request.use(
  (config) => {
    const newConfig = { ...config };
    const { accessToken } = sessionStorage;
    if (accessToken) {
      newConfig.headers.Authorization = `Bearer ${accessToken}`;
    }

    return newConfig;
  },
  (e) => {
    return Promise.reject(e);
  },
);

export default client;
