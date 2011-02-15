var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3281, "sl" : 7, "el" : 20, "name" : "AssertionErrorWrapper",
    "methods" : [
              {"sl" : 13, "el" : 15, "sc" : 5},  {"sl" : 17, "el" : 19, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_408" : {
					  "name" : "nameForStrictMock",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_161" : {
					  "name" : "summarizeSameObjectArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_419" : {
					  "name" : "nameForNiceMock",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_400" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_381" : {
					  "name" : "stubBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_305" : {
					  "name" : "noUpperLimitWithoutCallCountSet",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_209" : {
					  "name" : "atLeastTwoReturns",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_185" : {
					  "name" : "message",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_313" : {
					  "name" : "tooManyCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_288" : {
					  "name" : "mockWithNoExpectedCallsFailsAtFirstCall",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_181" : {
					  "name" : "moreThanOneArgument",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_236" : {
					  "name" : "twoThrows",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_296" : {
					  "name" : "defaultBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_132" : {
					  "name" : "testThreadNotSafe",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_85" : {
					  "name" : "exactCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_295" : {
					  "name" : "tooFewCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_142" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_297" : {
					  "name" : "tooManyCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_308" : {
					  "name" : "combination",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_215" : {
					  "name" : "exactCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_224" : {
					  "name" : "unorderedCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_321" : {
					  "name" : "callback",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_250" : {
					  "name" : "mixingOrderedAndUnordered",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_248" : {
					  "name" : "errorString",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_404" : {
					  "name" : "nameForMock",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_436" : {
					  "name" : "compareWithComparator",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_227" : {
					  "name" : "tooManyCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_14" : {
					  "name" : "argumentsOrdered",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_207" : {
					  "name" : "twoReturns",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_106" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_417" : {
					  "name" : "nameForMocksControl",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_228" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_262" : {
					  "name" : "resumeIfFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_384" : {
					  "name" : "range",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_279" : {
					  "name" : "mockWithOneExpectedCallFailsAtVerify",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_190" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_231" : {
					  "name" : "niceToStrict",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_293" : {
					  "name" : "mockWithOneExpectedCallFailsAtSecondCall",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_303" : {
					  "name" : "range",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_316" : {
					  "name" : "unorderedCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_292" : {
					  "name" : "differentMethods",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_158" : {
					  "name" : "wrongArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_202" : {
					  "name" : "moreThanOneArgument",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_433" : {
					  "name" : "defaultMatcherSetTooLate",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_269" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_394" : {
					  "name" : "differentMethods",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_192" : {
					  "name" : "wrongArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_193" : {
					  "name" : "summarizeSameObjectArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_253" : {
					  "name" : "argumentsOrdered",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
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
  [ 132 , 401 , 279 , 106 , 297 , 321 , 181 , 142 , 161 , 231 , 202 , 192 , 236 , 295 , 288 , 408 , 85 , 14 , 190 , 381 , 293 , 158 , 313 , 305 , 193 , 227 , 248 , 384 , 209 , 185 , 404 , 433 , 269 , 207 , 262 , 292 , 436 , 417 , 228 , 303 , 224 , 253 , 419 , 308 , 394 , 215 , 400 , 316 , 250 , 296   ] ,
  [ 132 , 401 , 279 , 106 , 297 , 321 , 181 , 142 , 161 , 231 , 202 , 192 , 236 , 295 , 288 , 408 , 85 , 14 , 190 , 381 , 293 , 158 , 313 , 305 , 193 , 227 , 248 , 384 , 209 , 185 , 404 , 433 , 269 , 207 , 262 , 292 , 436 , 417 , 228 , 303 , 224 , 253 , 419 , 308 , 394 , 215 , 400 , 316 , 250 , 296   ] ,
  [  ] ,
  [  ] ,
  [ 132 , 401 , 279 , 106 , 297 , 321 , 181 , 142 , 161 , 231 , 202 , 192 , 236 , 295 , 288 , 408 , 85 , 14 , 190 , 381 , 293 , 158 , 313 , 305 , 193 , 227 , 248 , 384 , 209 , 185 , 404 , 433 , 269 , 207 , 262 , 292 , 436 , 417 , 228 , 303 , 224 , 253 , 419 , 308 , 394 , 215 , 400 , 316 , 250 , 296   ] ,
  [ 132 , 401 , 279 , 106 , 297 , 321 , 181 , 142 , 161 , 231 , 202 , 192 , 236 , 295 , 288 , 408 , 85 , 14 , 190 , 381 , 293 , 158 , 313 , 305 , 193 , 227 , 248 , 384 , 209 , 185 , 404 , 433 , 269 , 207 , 262 , 292 , 436 , 417 , 228 , 303 , 224 , 253 , 419 , 308 , 394 , 215 , 400 , 316 , 250 , 296   ] ,
  [  ] ,
  [  ] 
];
