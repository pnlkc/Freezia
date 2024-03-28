import { useState, useEffect } from 'react';
// import startRecognition from './utils/voiceRecognization';
import ScreenFrame from './components/ScreenFrame';

import './assets/styles/main.css';
import { setToken } from './utils/firebase';

function App() {
  const [focused, setFocused] = useState(
    sessionStorage.getItem('focused')
      ? JSON.parse(sessionStorage.getItem('focused'))
      : false,
  );
  const [onScreen, setOnScreen] = useState(false);

  useEffect(() => {
    // startRecognition();
    setToken();
  }, []);
  useEffect(() => {
    if (focused) {
      if (JSON.parse(sessionStorage.getItem('focused')) === focused) {
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
          className={`${JSON.parse(sessionStorage.getItem('focused')) ? 'background-in' : 'background-out'} ${focused ? 'focus-in' : 'focus-out'}`}
          src="/images/background.png"
          alt="background"
          onClick={() => {
            setFocused(!focused);
          }}
        />
      </div>
      {onScreen && <ScreenFrame />}
      {/* <div id="container">
        <div id="result" />
      </div> */}
    </div>
  );
}

export default App;
