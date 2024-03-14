import { useState, useEffect } from 'react';
import startRecognition from './utils/voiceRecognization';
import ScreenFrame from './components/ScreenFrame';
import './assets/styles/main.css';

function App() {
  const [focused, setFocused] = useState(false);
  const [onScreen, setOnScreen] = useState(false);

  const forcedToggleFocused = (e) => {
    if (e.code === 'Space') setFocused(!focused);
  };

  useEffect(() => {
    startRecognition();
  }, []);

  useEffect(() => {
    window.addEventListener('keydown', forcedToggleFocused, {
      once: true,
    });
    if (focused) {
      setTimeout(() => {
        setOnScreen(true);
      }, 700);
    } else setOnScreen(false);
  }, [focused]);

  return (
    <div>
      <div className="background-crop">
        <img
          className={`background ${focused ? 'focus-in' : 'focus-out'}`}
          src="/images/background.png"
          alt="background"
        />
      </div>
      {onScreen && <ScreenFrame />}
      <div id="container">
        <div id="result" />
      </div>
    </div>
  );
}

export default App;
