import '../../../assets/styles/cooking/realtimemessage.css';

export default function RealTimeMessage({ recipe }) {
  return (
    <div className="real-time-message-container f-1">
      {recipe.reply === '' ? (
        <div className="real-time-chat-loading">
          요청에 맞는 레시피를 생성하고 있습니다...
        </div>
      ) : (
        <div className="real-time-chat">{recipe.reply}</div>
      )}
      {recipe.recipeList?.map((recipeInfo) => (
        <div className="create-recipe-info" key="unique">
          <h1 className="create-recipe-name">{recipeInfo.name}</h1>
          <div className="create-recipe-components">
            {recipeInfo.ingredientList.length > 0 && <h4>필요 식재료</h4>}
            {recipeInfo.ingredientList.map(({ name, amounts, unit }) => (
              <div key={name}>{`${name} ${amounts} ${unit}`}</div>
            ))}
            {recipeInfo.seasoningList.length > 0 && <h4>필요 조미료</h4>}
            {recipeInfo.seasoningList.map(({ name, amounts, unit }) => (
              <div key={name}>{`${name} ${amounts} ${unit}`}</div>
            ))}
          </div>
          <div className="create-recipe-info-box">
            {recipeInfo.cookTime !== '' && <h4>요리 정보</h4>}
            {recipeInfo.cookTime !== '' && (
              <span>{`요리 시간: ${recipeInfo.cookTime}`}</span>
            )}
            {recipeInfo.calorie !== '' && (
              <span>{`, 칼로리: ${recipeInfo.calorie}`}</span>
            )}
            {recipeInfo.servings !== '' && (
              <span>{`, 제공량: ${recipeInfo.servings}`}</span>
            )}
          </div>
          <div className="create-recipe-steps">
            {recipeInfo.recipeSteps.length > 0 && <h4>요리 방법</h4>}
            {recipeInfo.recipeSteps.map(({ name, description }, idx) => (
              <div key={name}>{`${idx + 1}. ${description}`}</div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}
