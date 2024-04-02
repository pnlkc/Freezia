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
      {recipeTypeList.map((filter) => (
        <div
          className={`filter-item f-0 box-shadow ${selectedList.includes(filter) ? 'filter-item-selected' : ''}`}
          key={filter}
          onClick={() => {
            selectItem(filter);
          }}
        >
          {filter}
        </div>
      ))}
    </div>
  );
}
