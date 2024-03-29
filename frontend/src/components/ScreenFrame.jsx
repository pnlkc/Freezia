import Screen from './Screen';
import '../assets/styles/screen.css';
import Footer from './Footer';

export default function ScreenFrame({ onScreen }) {
  return (
    <div
      className="screen-frame"
      style={{ display: onScreen ? 'flex' : 'none' }}
    >
      <Screen />
      <Footer />
    </div>
  );
}
