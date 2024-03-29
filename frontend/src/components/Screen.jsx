import {
  BrowserRouter as Router,
  Route,
  Routes,
  useLocation,
  useNavigate,
} from 'react-router-dom';
import { CSSTransition, TransitionGroup } from 'react-transition-group';

import Home from '../page/Home';
import Cooking from '../page/Cooking';
import { setNavigate } from '../utils/navigate';

function FadedRoutes() {
  const location = useLocation();
  const navigate = useNavigate();

  setNavigate(navigate);

  return (
    <TransitionGroup>
      <CSSTransition key={location.pathname} timeout={500} classNames="fade">
        <Routes location={location}>
          <Route exact path="/" element={<Home />} />
          <Route path="/cooking/*" element={<Cooking />} />
        </Routes>
      </CSSTransition>
    </TransitionGroup>
  );
}

export default function Screen() {
  return (
    <div className="screen-container">
      <Router>
        <FadedRoutes />
      </Router>
    </div>
  );
}
