import { Link } from 'react-router-dom';
import '../assets/styles/application.css';

export default function Application({ url, name }) {
  return (
    <Link to={`/${name}`} className="application-wrapper">
      <img className="applications-icon" src={url} alt={name} />
      <div className="applications-name">{name}</div>
    </Link>
  );
}
