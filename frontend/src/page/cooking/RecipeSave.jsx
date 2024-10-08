import { useEffect, useRef, useState } from 'react';
import '../../assets/styles/cooking/recipesave.css';
import { Link, useNavigate } from 'react-router-dom';
import { ingredientImageErrorHander } from '../../utils/imageErrorHandler';
import { getRecipeHistory, saveCompleteRecipe } from '../../apis/history';
import { disconnectWatch } from '../../apis/firebase';

export default function RecipeSave() {
  const recipeDetail = JSON.parse(sessionStorage.getItem('recipeInfo'));
  const [memo, setMemo] = useState('');
  const memoRef = useRef();

  const [addIngredientList, setAddIngredientsList] = useState(
    sessionStorage.getItem('addIngredientList')
      ? JSON.parse(sessionStorage.getItem('addIngredientList'))
      : [],
  );
  const [removeIngredientList, setRemoveIngredientList] = useState(
    sessionStorage.getItem('removeIngredientList')
      ? JSON.parse(sessionStorage.getItem('removeIngredientList'))
      : [],
  );
  const navigate = useNavigate();

  const changeHandler = (event) => {
    setMemo(memoRef.current.value);
  };

  useEffect(() => {
    sessionStorage.setItem(
      'addIngredientList',
      JSON.stringify(addIngredientList),
    );

    return () => {
      sessionStorage.removeItem('addIngredientList');
    };
  }, [addIngredientList]);

  useEffect(() => {
    sessionStorage.setItem(
      'removeIngredientList',
      JSON.stringify(removeIngredientList),
    );

    return () => {
      sessionStorage.removeItem('removeIngredientList');
    };
  }, [removeIngredientList]);

  return (
    <div className="recipe-save-container">
      <div className="recipe-save-title f-4 bold">
        <span
          className="recipe-save-back-button f-5"
          onClick={() => {
            sessionStorage.removeItem('addIngredientList');
            sessionStorage.removeItem('removeIngredientList');

            const { recipeId } = recipeDetail;
            const maxStep = JSON.parse(
              sessionStorage.getItem('recipeSteps'),
            ).length;

            sessionStorage.setItem('currentStep', maxStep);
            navigate(-1);
          }}
        >
          {'<'}
        </span>
        <span>요리 기록하기</span>
      </div>
      <div className="recipe-save-compoent-list-scroll">
        <div className="recipe-save-box box-shadow">
          <img
            className="recipe-save-image"
            src={`${recipeDetail.imgUrl}`}
            alt="음식 사진"
          />
          <div className="recipe-save-card-name f-3">{recipeDetail?.name}</div>
        </div>
        <div className="recipe-save-component-list-container">
          <div className="recipe-add-component-list-container">
            <div className="serving-select-title f-3 bold">메모</div>
            <textarea
              ref={memoRef}
              onChange={changeHandler}
              className="recipe-save-memo-input box-shadow"
            />
            <div className="component-save-list-menu">
              <div className="serving-select-title f-3 bold">
                새로 추가한 재료
              </div>
              <Link
                className="recipe-save-insert-button link f-1"
                to="/Cooking/recipe/1/save/insert"
              >
                +재료 추가하기
              </Link>
            </div>
            <div className="add-list-container">
              {addIngredientList.map(({ image, ingredientId, name }) => (
                <div className="add-list-item box-shadow" key={ingredientId}>
                  <img
                    src={image}
                    alt={name}
                    className="item-image"
                    onError={ingredientImageErrorHander}
                  />
                  <div className="save-add-item-name f-0">{name}</div>
                  {/* <img
                  className="item-delete-button"
                  src="/images/cooking/delete.svg"
                  alt="재료 삭제"
                /> */}
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="recipe-save-component-list-container">
          <div className="recipe-add-component-list-container">
            <div className="component-save-list-menu">
              <div className="serving-select-title f-3 bold">
                사용하지 않은 재료
              </div>
              <Link
                className="recipe-save-insert-button link f-1"
                to="/Cooking/recipe/1/save/delete"
              >
                - 재료 제거하기
              </Link>
            </div>
            <div className="add-list-container">
              {removeIngredientList.map(({ image, ingredientId, name }) => (
                <div className="add-list-item box-shadow" key={ingredientId}>
                  <img
                    src={image}
                    alt={name}
                    className="item-image"
                    onError={ingredientImageErrorHander}
                  />
                  <div className="save-add-item-name f-0">{name}</div>
                  {/* <img
                  className="item-delete-button"
                  src="/images/cooking/delete.svg"
                  alt="재료 삭제"
                /> */}
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
      <Link
        to={`/Cooking/recipe/${recipeDetail.recipeId}?listType=history`}
        className="recipe-save-button link box-shadow"
        onClick={() => {
          saveCompleteRecipe({
            recipeId: recipeDetail.recipeId,
            addIngredients: addIngredientList.map(({ name }) => {
              return { name };
            }),
            removeIngredients: removeIngredientList.map(({ name }) => {
              return { name };
            }),
            memo,
          });

          if (sessionStorage.isConnected === 'true') {
            disconnectWatch(recipeDetail.recipeId);
          }
          getRecipeHistory(recipeDetail.recipeId);

          sessionStorage.removeItem('addIngredientList');
          sessionStorage.removeItem('removeIngredientList');
        }}
      >
        저장
      </Link>
      <style>{'.footer-container { display : none } '}</style>
    </div>
  );
}
