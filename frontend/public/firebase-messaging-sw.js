importScripts(
  'https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js',
);

firebase.initializeApp({
  apiKey: 'AIzaSyCQWwpf7B34eIAtUwtGR0VWy-LxZYofzsg',
  authDomain: 'fridge-a3bc8.firebaseapp.com',
  projectId: 'fridge-a3bc8',
  storageBucket: 'fridge-a3bc8.appspot.com',
  messagingSenderId: '668301712530',
  appId: '1:668301712530:web:15bb4341c608af976bda4b',
  measurementId: 'G-416NJ95Y0R',
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log(
    '[firebase-messaging-sw.js] Received background message ',
    payload,
  );
  // 사용자 정의 알림 정보를 설정합니다.
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png',
  };

  self.registration.showNotification(notificationTitle, notificationOptions);

  self.clients.matchAll().then((test) => {
    console.log(test);
  });
  // clients.forEach((client) => {
  //   client.postMessage({
  //     type: 'PUSH_RECEIVED',
  //     payload: payload,
  //   });
  // });
});

self.addEventListener('install', () => {
  self.skipWaiting();
});

self.addEventListener('activate', () => {
  console.log('fcm sw activate..');
});

self.addEventListener('push', (e) => {
  console.log(e);
  const resultData = e.data.json().notification;
  const notificationTitle = resultData.title;
  const notificationOptions = {
    body: resultData.body,
    icon: resultData.image,
    tag: resultData.tag,
  };

  self.registration.showNotification(notificationTitle, notificationOptions);

  self.clients.matchAll().then((clients) => {
    clients.forEach((client) => {
      client.postMessage({
        type: 'DATA_UPDATED',
        data: {
          notificationTitle,
          notificationOptions,
        },
      });
    });
  });
});

self.addEventListener('message', (e) => {
  e.currentTarget.clients.matchAll().then((clients) => {
    console.log(clients);
  });
});

self.addEventListener('notificationclick', (event) => {
  console.log('notification click');
  const url = '/';
  event.notification.close();
  event.waitUntil(clients.openWindow(url));
});
