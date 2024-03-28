import { Link, useNavigate, useParams } from 'react-router-dom';
import { onMessage } from 'firebase/messaging';

import { useState, useEffect } from 'react';
import { connectWatch } from '../../apis/firebase';
import messaging from '../../utils/firebase';

import '../../assets/styles/cooking/watchconnect.css';
import '../../assets/styles/cooking/waitingconnect.css';

export default function WaitingConnect() {
  const { recipeId } = useParams();
  const navigate = useNavigate();
  const recipeDetail = JSON.parse(sessionStorage.recipeInfo);
  const [timer, setTimer] = useState(null);

  useEffect(() => {
    connectWatch(recipeId).then(() => {
      sessionStorage.setItem('isConnected', 'true');
      onMessage(messaging, (payload) => {
        console.log('Message received. ', payload);
      });
      setTimer(
        setTimeout(() => {
          navigate(`/Cooking/recipe/${recipeId}/steps/0`);
        }, 5000),
      );
    });
  }, []);

  return (
    <div
      className="watch-connect-container"
      style={{ backgroundImage: `url('${recipeDetail?.imgUrl}')` }}
    >
      <div
        className="connect-recipe-back-button"
        onClick={() => {
          if (timer) {
            clearTimeout(timer);
            setTimer(null);
          }
          navigate(`/Cooking/recipe/${recipeId}/connect`);
        }}
      >
        {'<'}
      </div>
      <div className="connect-recipe-info-box">
        <div className="connect-recipe-header-name f-4 bold">
          {recipeDetail.name}
        </div>
        <div className="connect-recipe-header-tags">
          {recipeDetail.recipeTypes.split(',').map((type) => (
            <div className="connect-recipe-header-tag" key={type}>
              {`#${type}`}
            </div>
          ))}
        </div>
      </div>
      <div className="waiting-connect-button-box">
        <div className="spinner center">
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
          <div className="spinner-blade" />
        </div>
        <div className="waiting-upper-description">
          <span className="sans bold f-0">Galaxy Watch</span>
          <span className="bold f-0">로 알림을 전송했습니다.</span>
        </div>
        <span className="bold f-0 waiting-lower-description">
          시작하기를 눌러주세요.
        </span>
        <div
          className="waiting-cancle-button"
          onClick={() => {
            if (timer) {
              clearTimeout(timer);
              setTimer(null);
            }
            navigate(`/Cooking/recipe/${recipeId}/connect`);
          }}
        >
          취소
        </div>
      </div>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
