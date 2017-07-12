package com.llb.pixabay.model.bean;

import java.util.ArrayList;

/**
 * Created by Derrick on 2017/7/12.
 * Example 1: Default image search
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&q=yellow+flowers&image_type=photo
 * <p>
 * Example 2: High resolution image search (requires permission)  response不一样，未使用
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&response_group=high_resolution&q=yellow+flower
 * <p>
 * Example 3: Retrieving images by ID or hash ID
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&id=11574
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&response_group=high_resolution&id=bb4d3acd9b2b4650
 * <p>
 * {
 * "total": 4692,
 * "totalHits": 500,
 * "hits": [{
 *      "id": 195893,
 *      "pageURL": "https://pixabay.com/en/blossom-bloom-flower-yellow-close-195893/",
 *       "type": "photo",
 *       "tags": "blossom, bloom, flower",
 *       "previewURL": "https://static.pixabay.com/photo/2013/10/15/09/12/flower-195893_150.jpg"
 *       "previewWidth": 150,
 *       "previewHeight": 84,
 *       "webformatURL": "https://pixabay.com/get/35bbf209db8dc9f2fa36746403097ae226b796b9e13e39d2_640.jpg",
 *       "webformatWidth": 640,
 *       "webformatHeight": 360,
 *       "imageWidth": 4000,
 *       "imageHeight": 2250,
 *       "imageSize": 4731420,
 *       "views": 7671,
 *       "downloads": 6439,
 *       "favorites": 1,
 *       "likes": 5,
 *       "comments": 2,
 *       "user_id": 48777,
 *       "user": "Josch13",
 *       "userImageURL": "https://static.pixabay.com/user/2013/11/05/02-10-23-764_250x250.jpg",
 * },{"id": 14724,...}]
 * }
 */

public class SearchImagesResponse {
    public String total;
    public String totalHits;
    public ArrayList<HitImages> hits;

    public class HitImages{
        public String id; //	A unique identifier for updating expired image URLs.
        public String total; //	The total number of hits.
        public String totalHits; //	The number of images accessible through the API. By default, the API is limited to return a maximum of 500 images per query.
        public String pageURL; //	Source page on Pixabay, which provides a download link for the original image of the dimension imageWidth x imageHeight and the file size imageSize.
        public String type;// 类型
        public String tags;// 图片标签
        public String previewURL; //	Low resolution images with a maximum width or height of 150 px (previewWidth x previewHeight).
        public String previewWidth; //
        public String previewHeight; //
        public String webformatURL; //Medium sized image with a maximum width or height of 640 px (webformatWidth x webformatHeight). URL valid for 24 hours. Replace '_640' in any webformatURL value to access other image sizes: Replace with '_180' or '_340' to get a 180 or 340 px tall version of the image, respectively. Replace with '_960' to get the image in a maximum dimension of 960 x 720 px.
        public String webformatWidth; //
        public String webformatHeight; //
        public String imageWidth; //
        public String imageHeight;//
        public String imageSize; //
        public String views; //	Total number of views.
        public String downloads; //	Total number of downloads.
        public String favorites; //	Total number of favorites.
        public String likes; //	Total number of likes.
        public String comments; //	Total number of comments.
        public String user_id; //
        public String user; //	User ID and name of the contributor. Profile URL: https://pixabay.com/users/{ USERNAME }-{ ID }/
        public String userImageURL; //	Profile picture URL (250 x 250 px).
    }
}
