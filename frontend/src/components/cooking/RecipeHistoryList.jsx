import '../../assets/styles/cooking/recipehistorylist.css';
import { ingredientImageErrorHander } from '../../utils/imageErrorHandler';

export default function RecipeHistoryList({ recipeHistory }) {
  return (
    <div className="recipe-history-list-container">
      {recipeHistory.map(
        ({
          completeDate,
          addIngredients,
          removeIngredients,
          completeCookId,
        }) => (
          <div className="history-list-box box-shadow" key={completeCookId}>
            <div className="histroy-complete-date">{completeDate}</div>
            <div className="history-ingredient-list">
              <div className="history-ingredient-add-list">
                <div className="histroy-ingredient-list-type">
                  <img
                    className="list-type-icon"
                    src="/images/cooking/plus_orange.svg"
                    alt="더하기"
                  />
                  <div className="list-type-title">새로 추가한 재료</div>
                </div>
                {addIngredients.map(
                  ({ ingredientId, name, image, amounts, unit }) => (
                    <div
                      className="history-ingredient-add-item"
                      key={ingredientId}
                    >
                      <img
                        className="add-item-image"
                        src={image}
                        alt={name}
                        onError={ingredientImageErrorHander}
                      />
                      <div className="add-item-name">{name}</div>
                      <div className="add-item-amount">
                        <span>{amounts}</span>
                        <span className="add-item-unit">{unit}</span>
                      </div>
                    </div>
                  ),
                )}
              </div>
              <div className="history-ingredient-remove-list">
                <div className="histroy-ingredient-list-type">
                  <img
                    className="list-type-icon"
                    src="/images/cooking/minus_orange.svg"
                    alt="빼기"
                  />
                  <div className="list-type-title">사용하지 않은 재료</div>
                </div>
                {removeIngredients.map(
                  ({ ingredientId, name, image, amounts, unit }) => (
                    <div
                      className="history-ingredient-remove-item"
                      key={ingredientId}
                    >
                      <img
                        className="remove-item-image"
                        src={image}
                        alt={name}
                        onError={ingredientImageErrorHander}
                      />
                      <div className="remove-item-name">{name}</div>
                      <div className="remove-item-amount">
                        <span>{amounts}</span>
                        <span className="add-item-unit">{unit}</span>
                      </div>
                    </div>
                  ),
                )}
              </div>
            </div>
          </div>
        ),
      )}
      {recipeHistory.length === 0 && <div>요리 기록이 없습니다.</div>}
    </div>
  );
}
