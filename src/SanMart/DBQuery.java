package SanMart;

public class DBQuery {
    public String ConnectionUrl = "jdbc:sqlite:alpha.db";
    public String ViewProduct = "SELECT Product.product_ID,Product.product_name,Product.madeBy,Product.costAvg FROM ClassifedProduct LEFT JOIN Product ON ClassifedProduct.product_ID = Product.product_ID LEFT JOIN Category ON ClassifedProduct.category_ID = Category.category_ID";

    public String ViewProductFruitVegetableGrain = ViewProduct+" WHERE Classified_name = \"채소\" OR Classified_name = \"과일\" OR Classified_name = \"곡류\" ORDER BY Product.product_name";
    public String ViewProductMilkColdFrozen = ViewProduct+" WHERE Classified_name = \"유제품\" OR Classified_name = \"냉장\" OR Classified_name = \"냉동\" ORDER BY Product.product_name";
    public String ViewProductProcessedCondiment = ViewProduct+" WHERE Classified_name = \"가공식품\" OR Classified_name = \"조미료\" ORDER BY Product.product_name";

    public String ViewProductCleanLaundryKitchen = ViewProduct+" WHERE Classified_name = \"청소\" OR Classified_name = \"세탁\" OR Classified_name = \"주방\" ORDER BY Product.product_name";
    public String ViewProductBathroomToilet = ViewProduct+" WHERE Classified_name = \"욕실\" OR Classified_name = \"화장실\" ORDER BY Product.product_name";
    public String ViewProductBabyBeautyDisposable = ViewProduct+" WHERE Classified_name = \"유아\" OR Classified_name = \"일회용품\" OR Classified_name = \"뷰티\" ORDER BY Product.product_name";


    public String ViewCartSimpole = "";


}
