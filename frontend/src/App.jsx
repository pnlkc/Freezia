import { useState, useEffect } from 'react';
import { onMessage } from 'firebase/messaging';
// import startRecognition from './utils/voiceRecognization';
import ScreenFrame from './components/ScreenFrame';

import './assets/styles/main.css';
import messaging, { setToken } from './utils/firebase';
import { sendWarning, setWatchToken } from './apis/firebase';
import navigateInstance from './utils/navigate';

function App() {
  const [focused, setFocused] = useState(
    sessionStorage.getItem('focused') &&
      sessionStorage.getItem('focused') !== 'undefined'
      ? JSON.parse(sessionStorage.getItem('focused'))
      : false,
  );
  const [onScreen, setOnScreen] = useState(false);

  useEffect(() => {
    // startRecognition();
    setToken();
    onMessage(messaging, (payload) => {
      const message = JSON.parse(payload.data.json);
      console.log(message);
      if (message.type === 1) {
        sessionStorage.setItem('warning', JSON.stringify(message));
        const { navigate } = navigateInstance;
        navigate('/Cooking/warning');
        setFocused(true);
      }
    });
  }, []);
  useEffect(() => {
    if (focused) {
      if (
        sessionStorage.getItem('focused') &&
        sessionStorage.getItem('focused') !== 'undefined' &&
        JSON.parse(sessionStorage.getItem('focused')) === focused
      ) {
        setOnScreen(true);
      } else {
        setTimeout(() => {
          setOnScreen(true);
        }, 700);
      }
    } else setOnScreen(false);

    sessionStorage.setItem('focused', JSON.stringify(focused));
  }, [focused]);

  return (
    <div>
      <div className="background-crop">
        <img
          className={`${
            sessionStorage.getItem('focused') &&
            sessionStorage.getItem('focused') !== 'undefined' &&
            JSON.parse(sessionStorage.getItem('focused'))
              ? 'background-in'
              : 'background-out'
          } ${focused ? 'focus-in' : 'focus-out'}`}
          src="/images/background.png"
          alt="background"
          onClick={() => {
            setFocused(!focused);
          }}
        />
      </div>
      <ScreenFrame onScreen={onScreen} />
      {/* <div id="container">
        <div id="result" />
      </div> */}
      {!focused && (
        <div
          className="open-door-button"
          onClick={() => {
            sendWarning();
          }}
        >
          문 열기
        </div>
      )}
    </div>
  );
}

export default App;
