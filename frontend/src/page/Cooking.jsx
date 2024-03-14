import { Routes, Route } from 'react-router-dom';
import UserSelect from './cooking/UserSelect';
import Main from './cooking/Main';
import '../assets/styles/cooking.css';

export default function Cooking() {
  return (
    <div className="cooking-container">
      <Routes>
        <Route path="/" element={<UserSelect />} />
        <Route path="/main" element={<Main />} />
      </Routes>
    </div>
  );
}
