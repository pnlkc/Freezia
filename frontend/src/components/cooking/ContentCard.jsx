export default function ContentCard({
  description,
  stepNumber,
  time,
  tip,
  type,
}) {
  return (
    <div className="recipe-step-content-card box-shadow">
      <img
        className="content-card-image"
        src={`/images/cooking/steps/${type}.png`}
        alt={description}
      />
      <div className="content-card-box">
        <div className="content-card-box-description">{description}</div>
        <div className="content-card-box-tip">{`Tip: ${tip}`}</div>
      </div>
    </div>
  );
}
