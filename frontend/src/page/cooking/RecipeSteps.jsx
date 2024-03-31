import { useState, useRef, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import '../../assets/styles/cooking/recipesteps.css';
import ContentCard from '../../components/cooking/ContentCard';
import registDragEvent, { inrange } from '../../utils/registDragEvent';
import RecipeFinsishCard from '../../components/cooking/RecipeFinishCard';
import { disconnectWatch, moveStep } from '../../apis/firebase';
import { registMessageEvent } from '../../utils/messageEventHandler';

export default function RecipeSteps() {
  const currentStep = useParams().step;
  const recipeDetail = JSON.parse(sessionStorage.getItem('recipeInfo'));
  const [step, setStep] = useState(+currentStep);
  const [transX, setTransX] = useState(0);
  const [animate, setAnimate] = useState(false);
  const [width, setWidth] = useState(700);
  const navigate = useNavigate();
  const slideRef = useRef();

  useEffect(() => {
    setWidth(slideRef.current.getBoundingClientRect().width);
    registMessageEvent((message) => {
      if (message.sender === 0 && sessionStorage.isConnected !== 'true') return;
      setStep(message.step - 1);
    }, 4);

    // registMessageEvent((message) => {
    //   navigate(`/Cooking/recipe/${recipeDetail.recipeId}/save`);
    // }, 3);

    const event = window.addEventListener('resize', () => {
      setWidth(slideRef.current.getBoundingClientRect().width);
    });

    return () => {
      disconnectWatch(recipeDetail.recipeId);
      window.removeEventListener('resize', event);
    };
  }, []);

  const recipeSteps = JSON.parse(sessionStorage.getItem('recipeSteps'));
  const maxStep = recipeSteps.length;

  return (
    <div className="recipe-step-container">
      <div
        className="recipe-step-back-button f-5 link"
        onClick={() => {
          navigate(-1);
        }}
      >
        {'<'}
      </div>

      <div
        className="recipe-step-finish-phrase f-3 bold"
        style={{
          top: step === maxStep ? '4vw' : '-10vw',
        }}
      >
        식사가 완성되었습니다
      </div>
      <div
        className={`recipe-step-progress ${step === maxStep ? 'progress-up' : ''}`}
      >
        <div
          className="recipe-step-progress-bar"
          style={{
            width: `${(inrange(step + 1, 0, maxStep) / maxStep) * 100}%`,
            borderRadius:
              inrange(step + 1, 0, maxStep) === maxStep ? '1vw' : '1vw 0 0 1vw',
          }}
        />
        <div
          className="recipe-step-progress-value"
          style={{
            color:
              inrange(step + 1, 0, maxStep) === maxStep ? 'white' : 'black',
            opacity: inrange(step + 1, 0, maxStep) === maxStep ? '1' : '0.5',
          }}
        >
          {`${inrange(step + 1, 0, maxStep)} / ${maxStep}`}
        </div>
      </div>
      <div
        className="recipe-step-content-card-container"
        ref={slideRef}
        style={{
          transform: `translateX(${-step * width + transX}px)`,
          transition: `transform ${animate ? 300 : 0}ms`,
        }}
        {...registDragEvent({
          onDragChange: (deltaX) => {
            setTransX(inrange(deltaX, -width + 10, width - 10));
          },
          onDragEnd: (deltaX) => {
            if (deltaX < -100) {
              const nextStep = inrange(step + 1, 0, maxStep);
              setStep(nextStep);
              moveStep(nextStep + 1);
            }
            if (deltaX > 100) {
              const nextStep = inrange(step - 1, 0, maxStep);
              setStep(nextStep);
              moveStep(nextStep + 1);
            }

            setAnimate(true);
            setTransX(0);
          },
        })}
      >
        {recipeSteps.map(
          ({ description, recipeStepId, stepNumber, timer, tip, type }) => (
            <ContentCard
              key={recipeStepId}
              description={description}
              stepNumber={stepNumber}
              time={timer}
              tip={tip}
              type={type}
            />
          ),
        )}
        <RecipeFinsishCard />
      </div>
      <div
        className={`recipe-step-content-stt-button box-shadow ${step === maxStep ? 'stt-button-down' : ''}`}
      >
        <img
          className="recipe-step-content-stt-button-icon"
          src="/images/cooking/speak.svg"
          alt="음성으로 듣기"
        />
      </div>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
