var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3482, "sl" : 14, "el" : 108, "name" : "ArrayMatcherTest",
    "methods" : [
              {"sl" : 18, "el" : 22, "sc" : 5},  {"sl" : 24, "el" : 28, "sc" : 5},  {"sl" : 30, "el" : 34, "sc" : 5},  {"sl" : 36, "el" : 40, "sc" : 5},  {"sl" : 42, "el" : 46, "sc" : 5},  {"sl" : 48, "el" : 52, "sc" : 5},  {"sl" : 54, "el" : 58, "sc" : 5},  {"sl" : 60, "el" : 64, "sc" : 5},  {"sl" : 66, "el" : 70, "sc" : 5},  {"sl" : 72, "el" : 76, "sc" : 5},  {"sl" : 78, "el" : 90, "sc" : 5},  {"sl" : 92, "el" : 97, "sc" : 5},  {"sl" : 99, "el" : 101, "sc" : 5},  {"sl" : 103, "el" : 107, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_187" : {
					  "name" : "toStringMixed",
					  "pass" : true ,
					  "methods" : [{"sl": 92 },],
					  "statements" : [{"sl": 94 },]
					  },
		"test_208" : {
					  "name" : "byteArray",
					  "pass" : true ,
					  "methods" : [{"sl": 24 },{"sl": 103 },],
					  "statements" : [{"sl": 26 },{"sl": 27 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_214" : {
					  "name" : "testToString",
					  "pass" : true ,
					  "methods" : [{"sl": 78 },{"sl": 99 },],
					  "statements" : [{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },{"sl": 87 },{"sl": 88 },{"sl": 89 },{"sl": 100 },]
					  },
		"test_218" : {
					  "name" : "floatArray",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },{"sl": 103 },],
					  "statements" : [{"sl": 44 },{"sl": 45 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_211" : {
					  "name" : "objectArray",
					  "pass" : true ,
					  "methods" : [{"sl": 66 },{"sl": 103 },],
					  "statements" : [{"sl": 68 },{"sl": 69 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_216" : {
					  "name" : "charArray",
					  "pass" : true ,
					  "methods" : [{"sl": 30 },{"sl": 103 },],
					  "statements" : [{"sl": 32 },{"sl": 33 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_219" : {
					  "name" : "intArray",
					  "pass" : true ,
					  "methods" : [{"sl": 48 },{"sl": 103 },],
					  "statements" : [{"sl": 50 },{"sl": 51 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_223" : {
					  "name" : "shortArray",
					  "pass" : true ,
					  "methods" : [{"sl": 60 },{"sl": 103 },],
					  "statements" : [{"sl": 62 },{"sl": 63 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_205" : {
					  "name" : "booleanArray",
					  "pass" : true ,
					  "methods" : [{"sl": 18 },{"sl": 103 },],
					  "statements" : [{"sl": 20 },{"sl": 21 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_217" : {
					  "name" : "doubleArray",
					  "pass" : true ,
					  "methods" : [{"sl": 36 },{"sl": 103 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_212" : {
					  "name" : "nonArray",
					  "pass" : true ,
					  "methods" : [{"sl": 72 },{"sl": 103 },],
					  "statements" : [{"sl": 74 },{"sl": 75 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
					  },
		"test_221" : {
					  "name" : "longArray",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },{"sl": 103 },],
					  "statements" : [{"sl": 56 },{"sl": 57 },{"sl": 104 },{"sl": 105 },{"sl": 106 },]
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
  [  ] ,
  [ 205   ] ,
  [  ] ,
  [ 205   ] ,
  [ 205   ] ,
  [  ] ,
  [  ] ,
  [ 208   ] ,
  [  ] ,
  [ 208   ] ,
  [ 208   ] ,
  [  ] ,
  [  ] ,
  [ 216   ] ,
  [  ] ,
  [ 216   ] ,
  [ 216   ] ,
  [  ] ,
  [  ] ,
  [ 217   ] ,
  [  ] ,
  [ 217   ] ,
  [ 217   ] ,
  [  ] ,
  [  ] ,
  [ 218   ] ,
  [  ] ,
  [ 218   ] ,
  [ 218   ] ,
  [  ] ,
  [  ] ,
  [ 219   ] ,
  [  ] ,
  [ 219   ] ,
  [ 219   ] ,
  [  ] ,
  [  ] ,
  [ 221   ] ,
  [  ] ,
  [ 221   ] ,
  [ 221   ] ,
  [  ] ,
  [  ] ,
  [ 223   ] ,
  [  ] ,
  [ 223   ] ,
  [ 223   ] ,
  [  ] ,
  [  ] ,
  [ 211   ] ,
  [  ] ,
  [ 211   ] ,
  [ 211   ] ,
  [  ] ,
  [  ] ,
  [ 212   ] ,
  [  ] ,
  [ 212   ] ,
  [ 212   ] ,
  [  ] ,
  [  ] ,
  [ 214   ] ,
  [  ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [ 214   ] ,
  [  ] ,
  [  ] ,
  [ 187   ] ,
  [  ] ,
  [ 187   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 214   ] ,
  [ 214   ] ,
  [  ] ,
  [  ] ,
  [ 205 , 211 , 218 , 212 , 219 , 208 , 223 , 216 , 221 , 217   ] ,
  [ 205 , 211 , 218 , 212 , 219 , 208 , 223 , 216 , 221 , 217   ] ,
  [ 205 , 211 , 218 , 212 , 219 , 208 , 223 , 216 , 221 , 217   ] ,
  [ 205 , 211 , 218 , 212 , 219 , 208 , 223 , 216 , 221 , 217   ] ,
  [  ] ,
  [  ] 
];
