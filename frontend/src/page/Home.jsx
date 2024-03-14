import Application from '../components/Application';
import Header from '../components/Header';
import Widget from '../components/Widget';

export default function Home() {
  const applicationList = [
    { url: '/images/home.svg', name: 'Home' },
    { url: '/images/food.png', name: 'Cooking' },
  ];

  return (
    <div>
      <Header />
      <div className="screen-body">
        <Widget />
        <div className="applications-box">
          {applicationList.map(({ url, name }) => (
            <Application url={url} name={name} key={url} />
          ))}
        </div>
      </div>
    </div>
  );
}
