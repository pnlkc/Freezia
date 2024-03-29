export default function FilterList({
  recipeTypeList,
  selectedList,
  setSelectedList,
}) {
  const selectItem = (idx) => {
    if (selectedList.includes(idx)) {
      setSelectedList(selectedList.filter((item) => item !== idx));
    } else setSelectedList([...selectedList, idx]);
  };

  return (
    <div className="filter-list-container">
      {recipeTypeList.map((filter, idx) => (
        <div
          className={`filter-item f-0 box-shadow ${selectedList.includes(idx) ? 'filter-item-selected' : ''}`}
          key={filter}
          onClick={() => {
            selectItem(idx);
          }}
        >
          {filter}
        </div>
      ))}
    </div>
  );
}
