//var root_url = "http://localhost:8000/";
var data_store = {
  comparison:{},
  product:{},
  queued_suggestions:[],
  current_comparison_link:{},
  all_aspects:{},
  product_cprs_list:{},
  object_update: function(new_data,target){
    var res = target;
    if(typeof res === "undefined") res = {};
    for(key in new_data){
      res[key] = new_data[key];
    }
    return res;
  }
};





