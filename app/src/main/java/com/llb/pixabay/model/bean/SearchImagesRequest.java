package com.llb.pixabay.model.bean;

/**
 * Created by Derrick on 2017/7/12.
 * Example 1: Default image search
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&q=yellow+flowers&image_type=photo
 *
 * Example 2: High resolution image search (requires permission)  response不一样，未使用
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&response_group=high_resolution&q=yellow+flower
 *
 * Example 3: Retrieving images by ID or hash ID
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&id=11574
 * https://pixabay.com/api/?key=5875982-38c8634638b32bb636ce3c020&response_group=high_resolution&id=bb4d3acd9b2b4650
 *
 */

public class SearchImagesRequest {
    public String key; // (required)	str	Your API key: 5875982-38c8634638b32bb636ce3c020
    public String q; //	str	A URL encoded search term. If omitted, all images are returned. This value may not exceed 100 characters. Example: "yellow+flower"
    public String lang; //	str	Language code of the language to be searched in.Accepted values: cs, da, de, en, es, fr, id, it, hu, nl, no, pl, pt, ro, sk, fi, sv, tr, vi, th, bg, ru, el, ja, ko, zh Default: "en"
    public String id; //	str	ID, hash ID, or a comma separated list of values for retrieving specific images. In a comma separated list, IDs and hash IDs cannot be used together.
    public String response_group; //	str	Choose between retrieving high resolution images and image details. When selecting details, you can access images up to a dimension of 960 x 720 px. Accepted values: "image_details", "high_resolution" (requires permission) Default: "image_details"
    public String image_type; //	str	Filter results by image type. Accepted values: "all", "photo", "illustration", "vector" Default: "all"
    public String orientation; //	str	Whether an image is wider than it is tall, or taller than it is wide. Accepted values: "all", "horizontal", "vertical" Default: "all"
    public String category; //	str	Filter results by category. Accepted values: fashion, nature, backgrounds, science, education, people, feelings, religion, health, places, animals, industry, food, computer, sports, transportation, travel, buildings, business, music
    public String min_width; //l	int	Minimum image width.Default: "0"
    public String min_height; //	int	Minimum image height.Default: "0"
    public String editors_choice; //	bool	Select images that have received an Editor's Choice award.Accepted values: "true", "false" Default: "false"
    public String safesearch; //	bool	A flag indicating that only images suitable for all ages should be returned. Accepted values: "true", "false" Default: "false"
    public String order; //	str	How the results should be ordered. Accepted values: "popular", "latest" Default: "popular"
    public String page; //	int	Returned search results are paginated. Use this parameter to select the page number. Default: 1
    public String per_page; //	int	Determine the number of results per page.Accepted values: 3 - 200 Default: 20
    public String callback; //	string	JSONP callback function name
    public String pretty; //	bool	Indent JSON output. This option should not be used in production.Accepted values: "true", "false" Default: "false"
}
