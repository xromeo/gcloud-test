package gcloud.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Created by Omar.Rivera on 3/1/23.
 *
 * @author Omar.Rivera
 */
@RegisterForReflection
public class StockOnHandMetadata {


    @JsonProperty("_id")
    public String _id;

    @JsonProperty("id")
    public String id;

    @JsonProperty("Retail_QTY_Match")
    public Boolean retailQuantityMatch;

    @JsonProperty("Retail_Product_Mate_Bundle")
    public Integer retailProductMateBundle;

    @JsonProperty("Retail_Product_UPCNumber")
    public String retailProductUpcNumber;

    @JsonProperty("source")
    public String source;

    @JsonProperty("Retail_SOHSKU")
    public String retailSohSku;

    @JsonProperty("Retail_Product_ImageURL")
    public String retailProductImageUrl;

    @JsonProperty("Retail_Sequence")
    public Long retailSequence;

    @JsonProperty("Retail_SOHQTY_SR")
    public Integer retailSohQuantityStockRoom;

    @JsonProperty("Retail_SOHDepositCode")
    public String retailSohDepositCode;

    @JsonProperty("Retail_SOHHoldArea")
    public Integer retailSohHoldArea;

    @JsonProperty("tsCoreIn")
    public Long tsCoreIn;

    @JsonProperty("Retail_Product_Brand")
    public String retailProductBrand;

    @JsonProperty("Retail_Product_Price")
    public String retailProductPrice;


    @JsonProperty("Retail_SOHQTY_SF")
    public Integer retailSohQuantitySalesFloor;
    @JsonProperty("Retail_Product_RFIDTagged")
    public String retailProductRfidTagged;
    @JsonProperty("Retail_Product_Manufacturer")
    public String retailProductManufacturer;
    @JsonProperty("Retail_Product_UPCs")
    public String retailProductUpcs;
    @JsonProperty("Retail_Product_Style")
    public String retailProductStyle;

    @JsonProperty("Retail_Product_Level1Name")
    public String retailProductLevel1Name;
    @JsonProperty("Retail_Product_Level2Name")
    public String retailProductLevel2Name;

    @JsonProperty("Retail_Product_Level1")
    public String retailProductLevel1;

    @JsonProperty("Retail_Product_Level2")
    public String retailProductLevel2;

    @JsonProperty("Retail_Product_Level3")
    public String retailProductLevel3;
    @JsonProperty("Retail_Product_Level4")
    public String retailProductLevel4;

    @JsonProperty("Retail_Product_Level5")
    public String retailProductLevel5;

    @JsonProperty("tsMongoIn")
    public Long tsMongoIn;


    @JsonProperty("Retail_SOHDateString")
    public String retailSohDateString;

    @JsonProperty("Retail_SOHStoreNumber")
    public String retailSohStoreNumber;


    @JsonProperty("Retail_SOHClearance")
    public Boolean retailSohClearance;


    @JsonProperty("Rule")
    public String rule;

    @JsonProperty("Retail_Product_Level3Name")
    public String retailProductLevel3Name;

    @JsonProperty("Retail_QTY_SF")
    public Integer retailQuantitySalesFloor;

    @JsonProperty("Retail_QTY_SR")
    public Integer retailQuantityStockRoom;

    @JsonProperty("Retail_SFMin")
    public Integer retailSalesFloorMin;

    @JsonProperty("Retail_Product_Dimension")
    public String retailProductDimension;

    @JsonProperty("Retail_SOHInTransitQTY")
    public Integer retailSohInTransitQuantity;

    @JsonProperty("tsEdgeIn")
    public Long tsEdgeIn;

    @JsonProperty("Retail_Product_UOM")
    public String retailProductUom;

    @JsonProperty("Retail_Product_Level4Name")
    public String retailProductLevel4Name;

    @JsonProperty("Retail_StoreNumber")
    public String retailStoreNumber;

    @JsonProperty("Retail_Product_UPC")
    public String retailProductUpc;

    @JsonProperty("Retail_SFMax")
    public Integer retailSalesFloorMax;

    @JsonProperty("Retail_SOHCurrency")
    public String retailSohCurrency;

    @JsonProperty("Retail_Product_ExternalSKU")
    public String retailProductExternalSku;

    @JsonProperty("Retail_Product_Level5Name")
    public String retailProductLevel5Name;

    @JsonProperty("Retail_SOHReceiptID")
    public String retailSohReceiptId;

    @JsonProperty("Retail_Product_VPN")
    public String retailProductVpn;

    @JsonProperty("Retail_SOHStoreInProcess")
    public Long retailSohStoreInProcess;

    @JsonProperty("Retail_SOHQTY")
    public Integer retailSohQuantity;

    @JsonProperty("Retail_SOHDepositName")
    public String retailSohDepositName;

    @JsonProperty("Retail_Product_Size")
    public String retailProductSize;

    @JsonProperty("Retail_QTY")
    public Integer retailQuantity;

    @JsonProperty("Retail_SOHPrice")
    public String retailSohPrice;

    @JsonProperty("Retail_Product_Name")
    public String retailProductName;

    @JsonProperty("Retail_SKU")
    public String retailProductSku;

    @JsonProperty("Retail_Product_Color")
    public String retailProductColor;

    @JsonProperty("Retail_SOHDate")
    public Long retailSohDate;

    public Long rowNumber;

    @Override
    public String toString() {
        return "StockOnHandMetadata{" +
                "_id='" + _id + '\'' +
                ", id='" + id + '\'' +
                ", retailQuantityMatch=" + retailQuantityMatch +
                ", retailProductMateBundle=" + retailProductMateBundle +
                ", retailProductUpcNumber='" + retailProductUpcNumber + '\'' +
                ", source='" + source + '\'' +
                ", retailSohSku='" + retailSohSku + '\'' +
                ", retailProductImageUrl='" + retailProductImageUrl + '\'' +
                ", retailSequence=" + retailSequence +
                ", retailSohQuantityStockRoom=" + retailSohQuantityStockRoom +
                ", retailSohDepositCode='" + retailSohDepositCode + '\'' +
                ", retailSohHoldArea=" + retailSohHoldArea +
                ", tsCoreIn=" + tsCoreIn +
                ", retailProductBrand='" + retailProductBrand + '\'' +
                ", retailProductPrice='" + retailProductPrice + '\'' +
                ", retailProductLevel1Name='" + retailProductLevel1Name + '\'' +
                ", retailSohQuantitySalesFloor=" + retailSohQuantitySalesFloor +
                ", retailProductRfidTagged='" + retailProductRfidTagged + '\'' +
                ", retailProductLevel2Name='" + retailProductLevel2Name + '\'' +
                ", retailProductLevel1='" + retailProductLevel1 + '\'' +
                ", retailProductLevel2='" + retailProductLevel2 + '\'' +
                ", retailProductLevel3='" + retailProductLevel3 + '\'' +
                ", tsMongoIn=" + tsMongoIn +
                ", retailProductLevel4='" + retailProductLevel4 + '\'' +
                ", retailProductLevel5='" + retailProductLevel5 + '\'' +
                ", retailSohDateString='" + retailSohDateString + '\'' +
                ", retailSohStoreNumber='" + retailSohStoreNumber + '\'' +
                ", retailProductManufacturer='" + retailProductManufacturer + '\'' +
                ", retailProductUpcs='" + retailProductUpcs + '\'' +
                ", retailSohClearance=" + retailSohClearance +
                ", retailProductStyle='" + retailProductStyle + '\'' +
                ", rule='" + rule + '\'' +
                ", retailProductLevel3Name='" + retailProductLevel3Name + '\'' +
                ", retailQuantitySalesFloor=" + retailQuantitySalesFloor +
                ", retailQuantityStockRoom=" + retailQuantityStockRoom +
                ", retailSalesFloorMin=" + retailSalesFloorMin +
                ", retailProductDimension='" + retailProductDimension + '\'' +
                ", retailSohInTransitQuantity=" + retailSohInTransitQuantity +
                ", tsEdgeIn=" + tsEdgeIn +
                ", retailProductUom='" + retailProductUom + '\'' +
                ", retailProductLevel4Name='" + retailProductLevel4Name + '\'' +
                ", retailStoreNumber='" + retailStoreNumber + '\'' +
                ", retailProductUpc='" + retailProductUpc + '\'' +
                ", retailSalesFloorMax=" + retailSalesFloorMax +
                ", retailSohCurrency='" + retailSohCurrency + '\'' +
                ", retailProductExternalSku='" + retailProductExternalSku + '\'' +
                ", retailProductLevel5Name='" + retailProductLevel5Name + '\'' +
                ", retailSohReceiptId='" + retailSohReceiptId + '\'' +
                ", retailProductVpn='" + retailProductVpn + '\'' +
                ", retailSohStoreInProcess=" + retailSohStoreInProcess +
                ", retailSohQuantity=" + retailSohQuantity +
                ", retailSohDepositName='" + retailSohDepositName + '\'' +
                ", retailProductSize='" + retailProductSize + '\'' +
                ", retailQuantity=" + retailQuantity +
                ", retailSohPrice='" + retailSohPrice + '\'' +
                ", retailProductName='" + retailProductName + '\'' +
                ", retailProductSku='" + retailProductSku + '\'' +
                ", retailProductColor='" + retailProductColor + '\'' +
                ", retailSohDate=" + retailSohDate +
                //", meta=" + meta +
                ", rowNumber=" + rowNumber +
                '}';
    }
}
