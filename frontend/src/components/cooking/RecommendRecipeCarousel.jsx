import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import registDragEvent, { inrange } from '../../utils/registDragEvent';
import { getRecipeDetail } from '../../apis/recipe';

export default function RecommendRecipeCarousel() {
  const [selected, setSelected] = useState(0);
  const [transX, setTransX] = useState(0);
  const [animate, setAnimate] = useState(false);
  const [width, setWidth] = useState(200);
  const slideRef = useRef();
  const navigate = useNavigate();

  const recipeList = JSON.parse(sessionStorage.getItem('recommendation'));

  const typeMap = {
    1: {
      keyword: '스트레스',
      description: '가 높으신날',
    },
    2: {
      keyword: '수면시간',
      description: '이 부족한날',
    },
    3: {
      keyword: '혈중산소',
      description: ' 관리에 좋은',
    },
    4: {
      keyword: '유통기한',
      description: '이 임박한 재료로 만든',
    },
  };

  useEffect(() => {
    setWidth(slideRef.current.getBoundingClientRect().width);
    const event = window.addEventListener('resize', () => {
      setWidth(slideRef.current.getBoundingClientRect().width);
    });

    return () => {
      window.removeEventListener('resize', event);
    };
  }, []);

  return (
    <div className="recommend-recipe-carousel-container">
      <div
        ref={slideRef}
        className="recommend-recipe-carousel-slider"
        style={{
          transform: `translateX(${-selected * width + transX}px)`,
          transition: `transform ${animate ? 300 : 0}ms`,
        }}
        {...registDragEvent({
          onDragChange: (deltaX) => {
            setTransX(inrange(deltaX, -width + 10, width - 10));
          },
          onDragEnd: (deltaX) => {
            const maxIndex = recipeList.length - 1;
            if (-10 < deltaX && deltaX < 10) {
              getRecipeDetail(recipeList[selected].recipeId).then(() => {
                navigate(`/Cooking/recipe/${recipeList[selected].recipeId}`);
              });
            }

            if (deltaX < -100) setSelected(inrange(selected + 1, 0, maxIndex));
            if (deltaX > 100) setSelected(inrange(selected - 1, 0, maxIndex));

            setAnimate(true);
            setTransX(0);
          },
          stopPropagation: true,
        })}
        onTransitionEnd={() => {
          setAnimate(false);
        }}
      >
        {recipeList.map(({ imgUrl, name, recipeId, recommendType }) => (
          <div className="recommend-recipe-carousel-item" key={recipeId}>
            <img
              className="carousel-item-image"
              src={imgUrl}
              alt={name}
              draggable={false}
            />
            <div className="carousel-item-description f-2">
              <span className="bold">{typeMap[recommendType].keyword}</span>
              <span>{typeMap[recommendType].description}</span>
              <div className="mt-0">
                <span className="bold c-p">{name}</span>
                <span> 어떠세요?</span>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className="carousel-pagenation">
        {Array.from({ length: recipeList.length }, (v, idx) => (
          <div
            className={
              idx === selected
                ? 'carousel-pagenation-checked'
                : 'carousel-pagenation-empty'
            }
            key={idx}
          />
        ))}
      </div>
    </div>
  );
}
