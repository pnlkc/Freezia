import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // /api 라는 문자열이 target 에 지정한 문자열 https://api.allsilver.com/ 로 변환되어 사용된다.
      // 예를 들어 요청 도메인이 http://localhost:5173/api/hello 라면 프록시에 의하여
      // https://api.allsilver.com/hello로 요청이 되는 것이다.
      '/api': {
        // 프록시가 적용될 요청 경로의 시작 부분. 클라이언트가 보낸 요청의 URL이 api로 시작되면 이 설정이 적용된다.
        target: 'https://j10s005.p.ssafy.io/api', // 사용할 요청 도메인을 설정한다.
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // 프록시 요청의 경로를 재작성하는 함수를 설정한다.
      },
    },
  },
});
