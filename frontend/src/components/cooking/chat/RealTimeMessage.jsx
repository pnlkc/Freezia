import '../../../assets/styles/cooking/realtimemessage.css';
import CreateRecipeCard from '../CreateRecipeCard';

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
        <>
          <CreateRecipeCard
            name={recipeInfo.name}
            imgUrl={recipeInfo.imgUrl}
            key="card"
          />
          <div className="create-recipe-info" key="unique">
            <h1 className="create-recipe-name">{recipeInfo.name}</h1>
            <div className="create-recipe-components">
              {recipeInfo.ingredientList.length > 0 && <h4>필요 식재료</h4>}
              {recipeInfo.ingredientList.map(({ name, amounts, unit }) => (
                <div key={name}>{`${name} ${amounts} ${unit}`}</div>
              ))}
              {recipeInfo.seasoningList.length > 0 &&
                recipeInfo.seasoningList[0].name !== '' && <h4>필요 조미료</h4>}
              {recipeInfo.seasoningList.map(({ name, amounts, unit }) => (
                <div
                  key={name}
                >{`${name} ${amounts} ${amounts.match(/['약간' | '조금']/) ? '' : unit}`}</div>
              ))}
            </div>
            <div className="create-recipe-info-box">
              {recipeInfo.cookTime !== '' && <h4>요리 정보</h4>}
              {recipeInfo.cookTime !== '' && (
                <span>{`요리 시간: ${recipeInfo.cookTime.match('분') ? recipeInfo.cookTime : `${Math.ceil(recipeInfo.cookTime / 60)}분`}`}</span>
              )}
              {recipeInfo.calorie !== '' && (
                <span>{`, 칼로리: ${recipeInfo.calorie.match(/['kcal' | 'Kcal']/) ? recipeInfo.calorie : `${recipeInfo.calorie}kcal`}`}</span>
              )}
              {recipeInfo.servings !== '' && (
                <span>{`, 제공량: ${recipeInfo.servings.match('인분') ? recipeInfo.servings : `${recipeInfo.servings}인분`}`}</span>
              )}
            </div>
            <div className="create-recipe-steps">
              {recipeInfo.recipeSteps.length > 0 && <h4>요리 방법</h4>}
              {recipeInfo.recipeSteps.map(({ name, description }, idx) => (
                <div
                  className="create-recipe-step-description"
                  key={name}
                >{`${idx + 1}. ${description}`}</div>
              ))}
            </div>
          </div>
        </>
      ))}
    </div>
  );
}
