import { useState, useEffect } from 'react';
// import startRecognition from './utils/voiceRecognization';
import ScreenFrame from './components/ScreenFrame';

import './assets/styles/main.css';
import { setToken } from './utils/firebase';
import navigateInstance from './utils/navigate';

import { sendWarning } from './apis/firebase';
import { registMessageEvent } from './utils/messageEventHandler';

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
    registMessageEvent((message) => {
      sessionStorage.setItem('warning', JSON.stringify(message));
      const { navigate } = navigateInstance;
      navigate('/Cooking/warning');
      setFocused(true);
    }, 1);
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
      {!focused && (
        <div
          className="focus-button"
          onClick={() => {
            setFocused(!focused);
          }}
        >
          패널 사용하기
        </div>
      )}
      {onScreen && (
        <div
          className="focus-out-left-button"
          onClick={() => {
            setFocused(!focused);
          }}
        />
      )}
      {onScreen && (
        <div
          className="focus-out-right-button"
          onClick={() => {
            setFocused(!focused);
          }}
        />
      )}
    </div>
  );
}

export default App;
