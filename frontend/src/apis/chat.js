const sendMessage = async ({
  ingredients,
  diseases,
  dislikeIngredients,
  prompt,
  onMessage,
}) => {
  const url = `generate-AI?ingredients=${ingredients}&diseases=${diseases}&dislikeIngredients=${dislikeIngredients}&prompt=${prompt}`;

  const eventSource = new EventSource(
    `${import.meta.env.VITE_BASE_URL}/${url}`,
  );

  eventSource.onmessage = (event) => {
    console.log(event);
    onMessage(event.data);
  };
};

export default sendMessage;
