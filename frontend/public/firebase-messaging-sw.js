importScripts(
  'https://www.gstatic.com/firebasejs/10.9.0/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/10.9.0/firebase-messaging-compat.js',
);

firebase.initializeApp({
  apiKey: 'AIzaSyARH_Be77DcpDwfuX_Ul9oPdTXgW1BxRBk',
  authDomain: 'fridgeisfree-6fca3.firebaseapp.com',
  projectId: 'fridgeisfree-6fca3',
  storageBucket: 'fridgeisfree-6fca3.appspot.com',
  messagingSenderId: '623127222721',
  appId: '1:623127222721:web:8938aed0fc5586dd853740',
  measurementId: 'G-JPLJ1LGCEB',
});

const messaging = firebase.messaging();

self.addEventListener('install', () => {
  self.skipWaiting();
});

self.addEventListener('activate', () => {
  console.log('fcm sw activate..');
});
