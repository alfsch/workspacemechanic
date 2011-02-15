var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3686, "sl" : 10, "el" : 24, "name" : "MethodSerializationWrapperTest",
    "methods" : [
             {"sl" : 17, "el" : 22, "sc" : 5}  ]}
    ,
    {"id" : 3686, "sl" : 12, "el" : 15, "name" : "MethodSerializationWrapperTest.A",
    "methods" : [
              {"sl" : 13, "el" : 14, "sc" : 9}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_307" : {
					  "name" : "testGetMethod",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 21 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 307   ] ,
  [  ] ,
  [ 307   ] ,
  [ 307   ] ,
  [ 307   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
