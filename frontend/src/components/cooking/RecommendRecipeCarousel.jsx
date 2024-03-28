import { useState, useRef, useEffect } from 'react';
import registDragEvent, { inrange } from '../../utils/registDragEvent';

export default function RecommendRecipeCarousel() {
  const [selected, setSelected] = useState(0);
  const [transX, setTransX] = useState(0);
  const [animate, setAnimate] = useState(false);
  const [width, setWidth] = useState(200);
  const slideRef = useRef();

  const recipeList = [
    {
      url: '/images/cooking/recipe/1.jpg',
      name: '애호박 두부면 볶음',
      description: '',
      id: 1,
    },
    {
      url: '/images/cooking/recipe/2.jpg',
      name: '버섯강된장',
      description: '',
      id: 2,
    },
    {
      url: '/images/cooking/recipe/3.jpg',
      name: '건새우 마늘쫑 조림',
      description: '',
      id: 3,
    },
    {
      url: '/images/cooking/recipe/4.jpg',
      name: '고단백 샐러드 국수',
      description: '',
      id: 4,
    },
    {
      url: '/images/cooking/recipe/5.jpg',
      name: '콜리플라워 리조또',
      description: '',
      id: 5,
    },
  ];

  useEffect(() => {
    setWidth(slideRef.current.getBoundingClientRect().width);
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

            if (deltaX < -100) setSelected(inrange(selected + 1, 0, maxIndex));
            if (deltaX > 100) setSelected(inrange(selected - 1, 0, maxIndex));

            setAnimate(true);
            setTransX(0);
          },
        })}
        onTransitionEnd={() => {
          setAnimate(false);
        }}
      >
        {recipeList.map(({ url, name, id }) => (
          <div className="recommend-recipe-carousel-item" key={id}>
            <img
              className="carousel-item-image"
              src={url}
              alt={name}
              draggable={false}
            />
            <div className="carousel-item-description f-2">
              <span className="bold">스트레스</span>
              <span>가 높으신날</span>
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
