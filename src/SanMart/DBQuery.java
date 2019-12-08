package SanMart;

public class DBQuery {
    public final static String ConnectionUrl = "jdbc:sqlite:alpha.db";
    public final static String ViewProduct = "SELECT Product.product_ID,Product.product_name,Product.madeBy,Product.costAvg FROM ClassifedProduct LEFT JOIN Product ON ClassifedProduct.product_ID = Product.product_ID LEFT JOIN Category ON ClassifedProduct.category_ID = Category.category_ID";

    public final static String ViewProductFruitVegetableGrain = ViewProduct+" WHERE Classified_name = \"채소\" OR Classified_name = \"과일\" OR Classified_name = \"곡류\" ORDER BY Product.product_name";
    public final static String ViewProductMilkColdFrozen = ViewProduct+" WHERE Classified_name = \"유제품\" OR Classified_name = \"냉장\" OR Classified_name = \"냉동\" ORDER BY Product.product_name";
    public final static String ViewProductProcessedCondiment = ViewProduct+" WHERE Classified_name = \"가공식품\" OR Classified_name = \"조미료\" ORDER BY Product.product_name";

    public final static String ViewProductCleanLaundryKitchen = ViewProduct+" WHERE Classified_name = \"청소\" OR Classified_name = \"세탁\" OR Classified_name = \"주방\" ORDER BY Product.product_name";
    public final static String ViewProductBathroomToilet = ViewProduct+" WHERE Classified_name = \"욕실\" OR Classified_name = \"화장실\" ORDER BY Product.product_name";
    public final static String ViewProductBabyBeautyDisposable = ViewProduct+" WHERE Classified_name = \"유아\" OR Classified_name = \"일회용품\" OR Classified_name = \"뷰티\" ORDER BY Product.product_name";


    public final static String ViewCartSimple = "SELECT product_ID,product_name,cart_count FROM v_CartSimple";
    public final static String InsertCartSimple = "INSERT INTO v_CartSimple(`product_ID`,`cart_count`) values (?,?)";
    public final static String UpdateCartSimple = "UPDATE v_CartSimple set `cart_count`=? WHERE `product_ID`=?";
    public final static String DeleteCartSimple = "DELETE FROM v_CartSimple WHERE `product_ID`=?";

    public final static String ViewCartDetail = "SELECT product_name, cart_count, mart_name, (mart_price * cart_count) as price_sum"
                                                        + " FROM v_CartPriceByMart"
                                                        + " LEFT JOIN Product On Product.product_id = Cart.product_id";

}
