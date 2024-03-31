import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import Header from '../../components/cooking/Header';

import '../../assets/styles/cooking/history.css';
import RecipeListBox from '../../components/cooking/RecipeListBox';
import { getCompleteRecipeList, getSaveRecipeList } from '../../apis/user';

export default function History() {
  const [selected, setSelected] = useState(
    sessionStorage.historyPage === 'completed' ? 'completed' : 'saved',
  );
  const location = useLocation();

  useEffect(() => {
    if (location.state?.selected) {
      setSelected(location.state.selected);
    }
  }, [location]);

  const [saveRecipeList, setSaveRecipeList] = useState(
    JSON.parse(sessionStorage.getItem('save')),
  );

  const [completeRecipeList, setCompleteRecipeList] = useState(
    JSON.parse(sessionStorage.getItem('complete')),
  );

  useEffect(() => {
    getSaveRecipeList().then((recipeList) => {
      setSaveRecipeList(recipeList);
    });

    getCompleteRecipeList().then((recipeList) => {
      setCompleteRecipeList(recipeList);
    });
  }, []);

  return (
    <div className="profile-history-container">
      <Header isHome={false} />
      <div className="profile-history-content-container">
        <div className="profile-history-select-box box-shadow">
          <div
            className={`profile-history-select ${selected === 'saved' ? 'history-seleceted' : ''}`}
            onClick={() => {
              setSelected('saved');
            }}
          >
            <img
              className="profile-history-select-icon"
              src={`/images/cooking/history/bookmark_${selected === 'saved' ? 'white' : 'orange'}.svg`}
              alt="저장한 레시피"
            />
            <span className="profile-history-select-title">저장한 레시피</span>
            <span className="profile-history-select-count">{`(${saveRecipeList.length})`}</span>
          </div>
          <div
            className={`profile-history-select ${selected === 'completed' ? 'history-seleceted' : ''}`}
            onClick={() => {
              setSelected('completed');
            }}
          >
            <img
              className="profile-history-select-icon"
              src={`/images/cooking/history/complete_${selected === 'completed' ? 'white' : 'orange'}.svg`}
              alt="완료한 요리"
            />
            <span className="profile-history-select-title">완료한 요리</span>
            <span className="profile-history-select-count">{`(${completeRecipeList.length})`}</span>
          </div>
        </div>
        <div
          className="profile-history-list-box"
          style={{
            transform: `translateX(calc(${selected === 'saved' ? '25%' : '-25.2%'}))`,
          }}
        >
          <RecipeListBox recipeList={saveRecipeList} selected={selected} />
          <RecipeListBox recipeList={completeRecipeList} selected={selected} />
        </div>
      </div>
    </div>
  );
}
