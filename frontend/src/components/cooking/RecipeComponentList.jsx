import { useEffect, useState } from 'react';

import '../../assets/styles/cooking/recipecomponent.css';
import { ingredientImageErrorHander } from '../../utils/imageErrorHandler';

export default function RecipeComponentList({
  ingredientList,
  seasoningList,
  baseServing,
}) {
  const [serving, setServing] = useState(baseServing);

  const increaseServing = () => {
    setServing(serving + 1);
  };

  const decreaseServing = () => {
    if (serving === 1) return;
    setServing(serving - 1);
  };

  const convertAmounts = (amounts) => {
    const amountsString = '' + amounts;
    const isFraction = amountsString.indexOf('/');
    if (isFraction < 0) return +amounts;

    const denominator = +amountsString.slice(0, isFraction);
    const numerator = +amountsString.slice(
      isFraction + 1,
      amountsString.length,
    );

    return denominator / numerator;
  };

  useEffect(() => {
    setServing(baseServing);
  }, [baseServing]);

  return (
    <div className="recipe-component-list-container">
      <div className="component-list-menu">
        <div className="serving-select-title f-3">제공량</div>
        <div className="serving-select-menu">
          <img
            src="/images/cooking/minus_orange.svg"
            alt="제공량 감소"
            className="serving-select-button"
            onClick={decreaseServing}
          />
          <div className="f-2 serving-value">{`${serving ? serving : 0} 인분`}</div>
          <img
            src="/images/cooking/plus_orange.svg"
            alt="제공량 추가"
            className="serving-select-button"
            onClick={increaseServing}
          />
        </div>
      </div>
      <div className="ingredient-list-box">
        <div className="component-list-title">식재료</div>
        <div className="ingredient-list-container">
          {ingredientList &&
            ingredientList.map(
              ({ ingredientId, image, amounts, name, unit }) => (
                <div className="ingredient-list-item" key={ingredientId}>
                  <img
                    src={image}
                    alt={name}
                    className="ingredient-item-image"
                    onError={ingredientImageErrorHander}
                  />
                  <div className="item-name">{name}</div>
                  <div className="item-amount">{`${(convertAmounts(amounts) * serving) / baseServing} ${unit}`}</div>
                </div>
              ),
            )}
        </div>
      </div>
      <div className="seasoning-list-box">
        <div className="component-list-title">조미료</div>
        <div className="ingredient-list-container">
          {seasoningList &&
            seasoningList.map(
              ({ ingredientId, image, amounts, name, unit }) => (
                <div className="ingredient-list-item" key={ingredientId}>
                  <img
                    src={image}
                    alt={name}
                    className="ingredient-item-image"
                    onError={ingredientImageErrorHander}
                  />
                  <div className="item-name">{name}</div>
                  <div className="item-amount">{`${(convertAmounts(amounts) * serving) / baseServing} ${unit}`}</div>
                </div>
              ),
            )}
        </div>
      </div>
    </div>
  );
}
