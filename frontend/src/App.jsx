import { useState, useEffect } from 'react';
// import startRecognition from './utils/voiceRecognization';
import ScreenFrame from './components/ScreenFrame';

import './assets/styles/main.css';
import { setToken } from './utils/firebase';
import navigateInstance from './utils/navigate';

import { sendWarning } from './apis/firebase';
import { registMessageEvent } from './utils/messageEventHandler';
import Ingredients from './components/Ingredients';

function App() {
  const handlBackgroundClass = (mode, oldMode) => {
    switch (mode) {
      case 'screen':
        return { background: 'background-screen', animation: 'focus-screen' };
      case 'fridge':
        return { background: 'background-fridge', animation: 'focus-fridge' };
      default:
        return {
          background: 'background-out',
          animation: `focus-out-${oldMode}`,
        };
    }
  };

  const handlBackgroundClassStart = (mode) => {
    switch (mode) {
      case 'screen':
        return { background: 'background-screen', animation: '' };
      case 'fridge':
        return { background: 'background-fridge', animation: '' };
      default:
        return { background: 'background-out', animation: '' };
    }
  };

  const [mode, setMode] = useState(
    sessionStorage.mode ? sessionStorage.mode : 'home',
  );
  const [onScreen, setOnScreen] = useState(false);
  const [background, setBackground] = useState(handlBackgroundClassStart(mode));
  const [onAnimate, setOnAnimate] = useState(false);
  const [onIngredientManager, setOnIngredientManager] = useState(false);

  const handleMode = (newMode) => {
    setBackground(handlBackgroundClass(newMode, mode));
    setMode(newMode);
  };

  useEffect(() => {
    // startRecognition();
    setToken();
    registMessageEvent((message) => {
      sessionStorage.setItem('warning', JSON.stringify(message));
      const { navigate } = navigateInstance;
      navigate('/Cooking/warning');
      handleMode('screen');
    }, 1);
  }, []);

  useEffect(() => {
    if (mode === 'screen') {
      if (sessionStorage.mode === 'screen') {
        setOnScreen(true);
      } else {
        setTimeout(() => {
          setOnScreen(true);
        }, 700);
      }
    } else {
      setOnScreen(false);
    }
    sessionStorage.setItem('mode', mode);
    setOnAnimate(true);
    setTimeout(() => {
      setOnAnimate(false);
    }, 700);
  }, [mode]);

  return (
    <div>
      <div className="background-crop">
        <img
          className={`${background.background} ${background.animation}`}
          src={`${mode === 'fridge' ? '/images/background_open.png' : '/images/background.png'}`}
          alt="background"
        />
      </div>
      <ScreenFrame onScreen={onScreen} />
      {/* <div id="container">
        <div id="result" />
      </div> */}
      {!onAnimate && mode === 'home' && (
        <div
          className="open-door-button"
          onClick={() => {
            // sendWarning();
            handleMode('fridge');
          }}
        >
          문 열기
        </div>
      )}
      {!onAnimate && mode === 'home' && (
        <div
          className="focus-button"
          onClick={() => {
            handleMode('screen');
          }}
        >
          스크린 사용하기
        </div>
      )}
      {!onAnimate && onScreen && (
        <div
          className="focus-out-left-button"
          onClick={() => {
            handleMode('home');
          }}
        />
      )}
      {!onAnimate && onScreen && (
        <div
          className="focus-out-right-button"
          onClick={() => {
            handleMode('home');
          }}
        />
      )}
      {!onAnimate && mode === 'fridge' && (
        <div
          className="fridge-focus-out-right-button"
          onClick={() => {
            handleMode('home');
          }}
        />
      )}
      {!onAnimate && mode === 'fridge' && (
        <div
          className="fridge-focus-out-left-button"
          onClick={() => {
            handleMode('home');
          }}
        />
      )}
      {!onAnimate && mode === 'fridge' && (
        <div
          className="fridge-select-ingredient-button"
          onClick={() => {
            // sendWarning();
            // handleMode('home');
            setOnIngredientManager(true);
          }}
        >
          식재료 꺼내기
        </div>
      )}
      {onIngredientManager && (
        <Ingredients
          setOnIngredientManager={setOnIngredientManager}
          handleMode={handleMode}
        />
      )}
    </div>
  );
}

export default App;
