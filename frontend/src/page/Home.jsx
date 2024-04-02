import Application from '../components/Application';
import Header from '../components/Header';
import Widget from '../components/Widget';

export default function Home() {
  const applicationList = [
    { url: '/images/home.svg', name: 'Home', path: 'home' },
    { url: '/images/logo.svg', name: 'FREEZIA', path: 'Cooking' },
  ];

  return (
    <div>
      <Header />
      <div className="screen-body">
        <Widget />
        <div className="applications-box">
          {applicationList.map(({ url, name, path }) => (
            <Application url={url} name={name} key={url} path={path} />
          ))}
        </div>
      </div>
    </div>
  );
}
