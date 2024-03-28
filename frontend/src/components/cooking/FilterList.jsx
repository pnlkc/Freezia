import { useState } from 'react';

export default function FilterList() {
  const [selectedList, setSelectedList] = useState([]);
  const filterList = [
    '한식',
    '양식',
    '중식',
    '일식',
    '밑반찬',
    '면요리',
    '볶음요리',
    '찜요리',
    '국물요리',
    '유통기한 임박',
  ];

  const selectItem = (idx) => {
    if (selectedList.includes(idx)) {
      setSelectedList(selectedList.filter((item) => item !== idx));
    } else setSelectedList([...selectedList, idx]);
  };

  return (
    <div className="filter-list-container">
      {filterList.map((filter, idx) => (
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
