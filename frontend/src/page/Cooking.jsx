import { Routes, Route } from 'react-router-dom';

import UserSelect from './cooking/UserSelect';
import Main from './cooking/Main';
import RecipeList from './cooking/RecipeList';
import Recipe from './cooking/Recipe';
import WatchConnect from './cooking/WatchConnect';
import RecipeSteps from './cooking/RecipeSteps';
import RecipeSave from './cooking/RecipeSave';
import RecipeInsert from './cooking/RecipeInsert';
import RecipeDelete from './cooking/RecipeDelete';
import RecipeCreate from './cooking/RecipeCreate';
import Setting from './cooking/Setting';
import Profile from './cooking/Profile';
import History from './cooking/History';
import Shopping from './cooking/Shopping';
import Warning from './cooking/Warning';
import WaitingConnect from './cooking/WaitingConnect';

import '../assets/styles/cooking.css';

export default function Cooking() {
  return (
    <div className="cooking-container">
      <Routes>
        <Route path="/" element={<UserSelect />} />
        <Route path="/main" element={<Main />} />
        <Route path="/warning" element={<Warning />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/profile/setting" element={<Setting />} />
        <Route path="/profile/history" element={<History />} />
        <Route path="/profile/shopping" element={<Shopping />} />
        <Route path="/recipe/list" element={<RecipeList />} />
        <Route path="/recipe/create" element={<RecipeCreate />} />
        <Route path="/recipe/:recipeId" element={<Recipe />} />
        <Route path="/recipe/:recipeId/connect" element={<WatchConnect />} />
        <Route
          path="/recipe/:recipeId/connect/waiting"
          element={<WaitingConnect />}
        />
        <Route path="/recipe/:recipeId/steps/:step" element={<RecipeSteps />} />
        <Route path="/recipe/:recipeId/save" element={<RecipeSave />} />
        <Route
          path="/recipe/:recipeId/save/insert"
          element={<RecipeInsert />}
        />
        <Route
          path="/recipe/:recipeId/save/delete"
          element={<RecipeDelete />}
        />
      </Routes>
    </div>
  );
}
