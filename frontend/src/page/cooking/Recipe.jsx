import { useState, useRef, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

import RecipeComponentList from '../../components/cooking/RecipeComponentList';
import RecipeHistoryList from '../../components/cooking/RecipeHistoryList';

import '../../assets/styles/cooking/recipe.css';
import registDragEvent, { inrange } from '../../utils/registDragEvent';
import { getRecipeDetail, getRecipeSteps } from '../../apis/recipe';
import { getRecipeHistory, toggleBookmark } from '../../apis/history';

export default function Recipe() {
  const [listType, setListType] = useState('component');
  const [transY, setTransY] = useState(-1);
  const [height, setHeight] = useState(0);

  const navigate = useNavigate();
  const draggableRef = useRef();
  const { recipeId } = useParams();

  const recipe = JSON.parse(sessionStorage.getItem('recipeList')).filter(
    ({ id }) => recipeId !== id,
  )[0];
  const [recipeDetail, setRecipeDetail] = useState(recipe);
  const [recipeSteps, setRecipeSteps] = useState(null);
  const [recipeHistory, setRecipeHistory] = useState([]);
  const [saveYn, setSaveYn] = useState(recipe.saveYn);

  useEffect(() => {
    const startTop = draggableRef.current.getBoundingClientRect().top;
    setHeight(startTop);
    setTransY(startTop);

    getRecipeDetail(recipeId).then((recipe) => {
      setRecipeDetail(recipe);
    });
    getRecipeSteps(recipeId).then((recipe) => {
      setRecipeSteps(recipe);
    });
    getRecipeHistory(recipeId).then((recipeHistoryList) => {
      setRecipeHistory(recipeHistoryList);
    });
  }, []);

  useEffect(() => {
    setSaveYn(recipeDetail.saveYn);
  }, [recipeDetail]);

  useEffect(() => {
    const recipeList = JSON.parse(sessionStorage.getItem('recipeList'));
    const changedRecipe = { ...recipe, saveYn };
    const newRecipeList = recipeList.map((oldRecipe) => {
      if (oldRecipe.recipeId === recipeId) return changedRecipe;
      return oldRecipe;
    });
    sessionStorage.setItem('recipeList', JSON.stringify(newRecipeList));
  }, [saveYn]);

  return (
    <div className="cooking-recipe-container">
      <div
        className="cooking-recipe-header"
        style={{
          backgroundImage: `url('${recipeDetail ? recipeDetail.imgUrl : ''}')`,
        }}
      >
        <div className="cooking-recipe-background-filter" />
        <div className="cooking-recipe-header-icon-box">
          <div
            className="f-5 recipe-header-icon"
            onClick={() => {
              navigate(-1);
            }}
          >
            {'<'}
          </div>
          <img
            src={
              saveYn
                ? '/images/cooking/bookmark_orange.svg'
                : '/images/cooking/bookmark.svg'
            }
            alt="북마크"
            className="header-icon-bookmark recipe-header-icon"
            onClick={() => {
              toggleBookmark(recipeId).then(setSaveYn(!saveYn));
            }}
          />
        </div>
        <div className="recipe-header-description-box">
          <div className="recipe-header-name f-3">
            {recipeDetail ? recipeDetail.name : ''}
          </div>
          <div className="recipe-header-tags">
            {recipeDetail?.recipeTypes.split(',').map((name) => (
              <div className="recipe-header-tag" key={name}>{`#${name}`}</div>
            ))}
          </div>
        </div>
      </div>
      <div
        className="cooking-recipe-info-container"
        style={{
          top: `${transY >= 0 ? `${transY}px` : '23vw'}`,
          height: `${transY >= 0 ? `calc(100% - ${transY}px)` : 'calc(100% - 23vw)'}`,
        }}
      >
        <div
          ref={draggableRef}
          className="cooking-recipe-info-draggable"
          draggable={false}
          {...registDragEvent({
            onDragChange: (_, deltaY) => {
              setTransY(inrange(transY + deltaY, 0, height));
            },
            onDragEnd: (_, deltaY) => {
              setTransY(inrange(transY + deltaY, 0, height));
            },
          })}
        />
        <div className="cooking-recipe-info-wrapper">
          <div className="recipe-info-box">
            <div className="recipe-info-wrapper">
              <div className="recipe-info-type">요리 시간</div>
              <div className="recipe-info-value">{`${recipeDetail ? recipeDetail.cookTime : 0} m`}</div>
            </div>
            <div className="recipe-info-divider" />
            <div className="recipe-info-wrapper">
              <div className="recipe-info-type">칼로리</div>
              <div className="recipe-info-value">{`${recipeDetail ? recipeDetail.calorie : 0}kcal`}</div>
            </div>
          </div>
          <Link to="connect" className="recipe-connect-link link f-0">
            상세 레시피 보기
          </Link>
          <div className="recipe-info-container-separator" />
          <div className="recipe-detail-menu">
            <div
              className={`recipe-detail-menu-item ${listType === 'component' ? 'menu-selected' : ''}`}
              onClick={() => {
                setListType('component');
              }}
            >
              필요한 재료
            </div>
            <div
              className={`recipe-detail-menu-item ${listType === 'history' ? 'menu-selected' : ''}`}
              onClick={() => {
                setListType('history');
              }}
            >
              내 요리 기록
            </div>
          </div>
          <div className="recipe-detail-container">
            <div
              className={`recipe-detail-wrapper ${listType === 'history' ? 'slide-right' : ''}`}
            >
              <RecipeComponentList
                ingredientList={recipeDetail?.ingredientList}
                seasoningList={recipeDetail?.seasoningList}
                baseServing={recipeDetail?.serving}
              />
              <RecipeHistoryList recipeHistory={recipeHistory} />
            </div>
          </div>
        </div>
      </div>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
