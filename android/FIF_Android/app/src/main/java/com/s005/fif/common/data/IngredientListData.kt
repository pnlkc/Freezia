package com.s005.fif.common.data

import com.s005.fif.recipe.dto.IngredientItem

data class IngredientItemData(
    val ingredientId: Int,
    val name: String,
    val imgUrl: String,
    val seasoningYn: Boolean,
    val expirationPeriod: Int
)

fun IngredientItemData.toIngredientItem() = IngredientItem(
    ingredientId = ingredientId,
    name = name,
    image = imgUrl,
    amounts = "1",
    unit = "개"
)

object IngredientListData {
    val list: List<IngredientItemData> = listOf(
        IngredientItemData(1, "복숭아", "https://cdn.pixabay.com/photo/2017/07/12/18/00/peach-2497691_1280.png", false, 5),
        IngredientItemData(2, "사과", "https://cdn.pixabay.com/photo/2017/09/10/18/11/apple-2736410_1280.png", false, 30),
        IngredientItemData(3, "딸기", "https://cdn.pixabay.com/photo/2018/07/27/02/30/strawberries-3565102_1280.png", false, 2),
        IngredientItemData(4, "바나나", "https://cdn.pixabay.com/photo/2016/02/23/17/29/banana-1218133_1280.png", false, 7),
        IngredientItemData(5, "수박", "https://cdn.pixabay.com/photo/2013/01/08/01/25/watermelon-74342_1280.jpg", false, 10),
        IngredientItemData(6, "배", "https://cdn.pixabay.com/photo/2018/01/15/14/04/healthy-3084002_1280.png", false, 5),
        IngredientItemData(7, "아보카도", "https://cdn.pixabay.com/photo/2016/03/05/19/03/appetite-1238257_1280.jpg", false, 5),
        IngredientItemData(8,"망고", "https://cdn.pixabay.com/photo/2016/02/23/17/36/mango-1218147_1280.png", false, 6),
        IngredientItemData(9,"오렌지", "https://cdn.pixabay.com/photo/2020/04/04/01/33/navel-5000527_1280.png", false, 7),
        IngredientItemData(10, "멜론", "https://cdn.pixabay.com/photo/2017/07/11/19/32/honeydew-2494765_1280.png", false, 10),
        IngredientItemData(11, "블루베리", "https://cdn.pixabay.com/photo/2014/11/20/13/54/blueberry-539135_1280.png", false, 7),
        IngredientItemData(12, "귤", "https://cdn.pixabay.com/photo/2017/09/26/19/43/fruit-2789849_960_720.png", false, 21),
        IngredientItemData(13, "자두", "https://cdn.pixabay.com/photo/2023/08/15/21/18/plums-8192860_1280.jpg", false, 7),
        IngredientItemData(14, "자몽", "https://cdn.pixabay.com/photo/2021/03/20/15/06/grapefruit-6110019_1280.jpg", false, 21),
        IngredientItemData(15, "체리", "https://cdn.pixabay.com/photo/2017/07/19/13/28/cherry-2519077_1280.png", false, 7),
        IngredientItemData(16, "청포도", "https://cdn.pixabay.com/photo/2016/03/26/23/42/grapes-1281910_1280.jpg", false, 15),
        IngredientItemData(17, "포도", "https://cdn.pixabay.com/photo/2017/07/20/02/14/grapes-2520999_1280.png", false, 15),
        IngredientItemData(18, "파인애플", "https://cdn.pixabay.com/photo/2016/12/18/23/16/pineapple-1916996_1280.png", false, 3),
        IngredientItemData(19, "레몬", "https://cdn.pixabay.com/photo/2018/04/18/11/33/lemon-3330200_960_720.png", false, 30),
        IngredientItemData(20, "키위", "https://cdn.pixabay.com/photo/2017/08/23/14/11/kiwi-2673038_1280.png", false, 14),
        IngredientItemData(21, "파", "https://cdn.pixabay.com/photo/2017/08/17/22/28/leek-2653127_1280.jpg", false, 21),
        IngredientItemData(22, "양파", "https://cdn.pixabay.com/photo/2016/03/05/19/15/bulb-1238338_1280.jpg", false, 30),
        IngredientItemData(23, "마늘", "https://cdn.pixabay.com/photo/2017/10/06/08/50/garlic-2822329_1280.png", false, 7),
        IngredientItemData(24, "상추", "https://cdn.pixabay.com/photo/2017/07/03/18/14/lettuce-2468495_1280.png", false, 5),
        IngredientItemData(25, "배추", "https://cdn.pixabay.com/photo/2013/01/08/01/43/chinese-cabbage-74360_1280.jpg", false, 30),
        IngredientItemData(26, "양배추", "https://cdn.pixabay.com/photo/2017/09/19/11/41/kale-2765041_1280.png", false, 14),
        IngredientItemData(27, "버섯", "https://cdn.pixabay.com/photo/2015/08/29/20/59/mushroom-913499_1280.jpg", false, 7),
        IngredientItemData(28, "당근", "https://cdn.pixabay.com/photo/2016/11/04/20/08/carrots-1798840_1280.jpg", false, 14),
        IngredientItemData(29, "오이", "https://cdn.pixabay.com/photo/2017/07/10/02/33/cucumber-2488875_1280.png", false, 5),
        IngredientItemData(30, "감자", "https://cdn.pixabay.com/photo/2012/12/24/08/39/agriculture-72254_1280.jpg", false, 30),
        IngredientItemData(31, "고구마", "https://cdn.pixabay.com/photo/2018/05/12/00/53/sweet-potato-3392187_1280.png", false, 30),
        IngredientItemData(32, "가지", "https://cdn.pixabay.com/photo/2010/12/13/09/51/aubergine-1809_1280.jpg", false, 5),
        IngredientItemData(33, "호박", "https://cdn.pixabay.com/photo/2017/09/15/08/01/pumpkin-2751383_1280.png", false, 14),
        IngredientItemData(34, "두부", "https://cdn.pixabay.com/photo/2019/03/26/01/14/tofu-4081697_1280.jpg", false, 7),
        IngredientItemData(35, "계란", "https://cdn.pixabay.com/photo/2010/12/13/09/51/bird-1772_1280.jpg", false, 30),
        IngredientItemData(36, "콩나물", "https://cdn.pixabay.com/photo/2013/07/18/15/08/green-bean-bud-164652_1280.jpg", false, 5),
        IngredientItemData(37, "무", "https://cdn.pixabay.com/photo/2015/03/28/18/13/icicle-radishes-696288_960_720.jpg", false, 14),
        IngredientItemData(38, "고추", "https://cdn.pixabay.com/photo/2018/01/29/22/33/pepperoni-3117442_1280.jpg", false, 7),
        IngredientItemData(39, "피망", "https://cdn.pixabay.com/photo/2017/07/09/05/36/vegetable-2486289_960_720.png", false, 7),
        IngredientItemData(40, "브로콜리", "https://cdn.pixabay.com/photo/2016/06/11/15/33/broccoli-1450274_960_720.png", false, 7),
        IngredientItemData(41, "소고기", "https://cdn.pixabay.com/photo/2020/09/13/15/40/beef-5568607_1280.jpg", false, 3),
        IngredientItemData(42, "돼지고기", "https://cdn.pixabay.com/photo/2019/04/28/18/24/sideburn-4163932_1280.jpg", false, 3),
        IngredientItemData(43, "닭고기", "https://cdn.pixabay.com/photo/2018/03/09/17/41/chicken-3212144_1280.jpg", false, 3),
        IngredientItemData(44, "양고기", "https://cdn.pixabay.com/photo/2016/03/05/21/57/abstract-1239142_1280.jpg", false, 3),
        IngredientItemData(45, "닭가슴살", "https://cdn.pixabay.com/photo/2014/03/05/01/20/chicken-breast-279849_1280.jpg", false, 3),
        IngredientItemData(46, "삼겹살", "https://cdn.pixabay.com/photo/2016/01/05/09/48/pork-1122171_1280.jpg", false, 3),
        IngredientItemData(47, "새우", "https://cdn.pixabay.com/photo/2017/06/11/22/55/shrimp-2393818_1280.jpg", false, 2),
        IngredientItemData(48, "문어", "https://cdn.pixabay.com/photo/2015/03/25/19/38/squid-689385_1280.jpg", false, 2),
        IngredientItemData(49, "오징어", "https://cdn.pixabay.com/photo/2016/06/01/10/21/food-1428735_1280.jpg", false, 2),
        IngredientItemData(50, "고등어", "https://images.unsplash.com/photo-1554071407-1fb7259a9118?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", false, 2),
        IngredientItemData(51, "게", "https://cdn.pixabay.com/photo/2014/04/25/19/52/crab-332103_1280.png", false, 2),
        IngredientItemData(52, "연어", "https://cdn.pixabay.com/photo/2016/11/09/19/32/wild-1812516_1280.png", false, 2),
        IngredientItemData(53,"전복", "https://cdn.pixabay.com/photo/2020/06/01/11/51/abalone-5246386_960_720.jpg", false, 2),
        IngredientItemData(54,"굴", "https://cdn.pixabay.com/photo/2018/08/28/11/28/petrified-3637248_1280.jpg", false, 2),
        IngredientItemData(55,"아귀", "https://cdn.pixabay.com/photo/2016/06/09/12/14/monkfish-1445689_1280.jpg", false, 2),
        IngredientItemData(56,"멸치", "https://cdn.pixabay.com/photo/2020/09/06/16/26/anchovy-5549405_1280.jpg", false, 2),
        IngredientItemData(57,"꼬막", "https://cdn.pixabay.com/photo/2015/02/26/09/59/shell-650090_1280.jpg", false, 2),
        IngredientItemData(58,"멍게", "https://cdn.pixabay.com/photo/2020/03/21/04/00/squirt-4952620_1280.jpg", false, 2),
        IngredientItemData(59,"바지락", "https://cdn.pixabay.com/photo/2020/01/06/19/11/clam-4746021_1280.jpg", false, 2),
        IngredientItemData(60,"갈치", "https://cdn.pixabay.com/photo/2016/12/30/04/26/restaurant-1939958_1280.jpg", false, 2),
        IngredientItemData(61,"굴비", "https://cdn.pixabay.com/photo/2019/09/25/09/29/jeollanam-do-4503138_1280.jpg", false, 2),
        IngredientItemData(62,"낙지", "https://cdn.pixabay.com/photo/2015/05/12/09/31/octopus-763758_1280.jpg", false, 2),
        IngredientItemData(63,"우유", "https://cdn.pixabay.com/photo/2017/09/11/23/34/milk-bottle-2740848_1280.jpg", false, 7),
        IngredientItemData(64,"치즈", "https://cdn.pixabay.com/photo/2016/03/05/19/24/cheese-1238395_1280.jpg", false, 14),
        IngredientItemData(65,"버터", "https://cdn.pixabay.com/photo/2016/06/11/04/09/butter-1449453_1280.jpg", false, 14),
        IngredientItemData(66,"슬라이스 치즈", "https://cdn.pixabay.com/photo/2015/01/08/02/46/cheese-592280_1280.jpg", false, 14),
        IngredientItemData(67,"요거트", "https://cdn.pixabay.com/photo/2016/10/31/18/25/yogurt-1786329_960_720.jpg", false, 7),
        IngredientItemData(68,"아이스크림", "https://cdn.pixabay.com/photo/2017/09/08/05/46/ice-2727806_960_720.png", false, 30),
        IngredientItemData(69,"만두", "https://cdn.pixabay.com/photo/2023/12/15/05/31/food-8450081_960_720.jpg", false, 7),
        IngredientItemData(70,"햄", "https://cdn.pixabay.com/photo/2019/12/25/12/37/ham-4718476_1280.jpg", false, 7),
        IngredientItemData(71,"베이컨", "https://cdn.pixabay.com/photo/2019/07/16/20/04/bacon-4342494_1280.jpg", false, 7),
        IngredientItemData(72,"떡갈비", "https://cdn.pixabay.com/photo/2015/05/03/13/51/korean-tteokgalbi-751205_1280.jpg", false, 5),
        IngredientItemData(73,"치킨 너겟", "https://cdn.pixabay.com/photo/2016/04/25/07/31/chicken-nuggets-1351329_1280.jpg", false, 5),
        IngredientItemData(74,"피자", "https://cdn.pixabay.com/photo/2017/12/05/20/10/pizza-3000285_1280.png", false, 3),
        IngredientItemData(75,"소세지", "https://cdn.pixabay.com/photo/2014/08/26/15/24/sausage-428067_1280.jpg", false, 5),
        IngredientItemData(76,"돈까스", "https://cdn.pixabay.com/photo/2016/09/23/23/23/restaurant-1690696_1280.jpg", false, 3),
        IngredientItemData(77,"총각김치", "https://cdn.pixabay.com/photo/2022/11/24/02/06/kimchi-7613325_1280.jpg", false, 10),
        IngredientItemData(78,"김치", "https://cdn.pixabay.com/photo/2022/11/24/02/06/kimchi-7613328_1280.jpg", false, 14),
        IngredientItemData(79,"파스타면", "https://cdn.pixabay.com/photo/2016/06/17/19/09/pasta-1463918_1280.jpg", false, 365),
        IngredientItemData(80,"감", "https://cdn.pixabay.com/photo/2013/01/08/01/13/sharon-74199_1280.jpg", false, 7),
        IngredientItemData(81,"시금치", "https://cdn.pixabay.com/photo/2022/08/27/04/00/vegetables-7413568_1280.jpg", false, 5),
        IngredientItemData(82,"느타리버섯", "https://cdn.pixabay.com/photo/2017/12/16/22/52/mushroom-3023460_1280.jpg", false, 5),
        IngredientItemData(83,"애호박", "https://cdn.pixabay.com/photo/2018/04/13/03/44/zucchini-3315476_960_720.jpg", false, 10),
        IngredientItemData(84,"청양고추", "https://cdn.pixabay.com/photo/2020/09/08/09/08/chilies-5554063_1280.jpg", false, 5),
        IngredientItemData(85,"된장", "https://cdn.pixabay.com/photo/2021/01/22/06/16/doenjang-5939216_1280.jpg", true, 30),
        IngredientItemData(86,"진간장", "https://cdn.pixabay.com/photo/2019/11/25/15/22/soy-sauce-4652303_1280.jpg", true, 30),
        IngredientItemData(87,"고춧가루", "https://img.freepik.com/free-photo/top-view-bowl-with-spices_23-2149439571.jpg?t=st=1710404841~exp=1710408441~hmac=a6503d755b8717340de9ed91ed677b1fb94c0ccf446354b75d091476388b0274&w=996", true, 365),
        IngredientItemData(88,"다진 마늘", "https://img.freepik.com/free-photo/fresh-raw-garlic-ready-cook_1150-42637.jpg?t=st=1710404872~exp=1710408472~hmac=8f2dcda61a2067045a986ee7468e08780d508cad037dcf47aa2fcbb6265bd3bf&w=996", true, 7),
        IngredientItemData(89,"설탕", "https://img.freepik.com/free-photo/world-diabetes-day-sugar-cubes-glass-bowl-dark-floor_1150-26665.jpg?t=st=1710404891~exp=1710408491~hmac=6a5d2dd45e86486485aa99c21140574d1f07711639d4603da488dba54eaf09d9&w=996", true, 365),
        IngredientItemData(90,"소금", "https://img.freepik.com/free-photo/spoon-heap-salt-table_144627-11035.jpg?t=st=1710404899~exp=1710408499~hmac=f9181190bf34862cbfdabaf4d79d8b9291a86e31b91943d4448c135bf026bbc3&w=996", true, 365),
        IngredientItemData(91,"토마토", "https://cdn.pixabay.com/photo/2021/10/14/03/19/tomato-6707992_1280.png", false, 7),
    )

    val map = list.associate { it.ingredientId to it.name }

    val nameMap = list.associate { it.name to it.imgUrl }

    val mapIdToItem = list.associateBy { it.ingredientId }

    private val mapNameToItem = list.associateBy { it.name }

    fun getImageByName(name: String): String {
        return mapNameToItem[name]?.imgUrl ?: ""
    }
}