package BOT.Objects;

public class FilterList {
    private static String[] List= {
            "간나새끼", "갈보", "개간년", "개년", "개새끼", "씨발", "지랄", "족새", "꼰대", 
            "느금마", "니미", "니기미", "닥쳐", "닥처", "대가리", " 등신", "딸딸이", "똘추", 
            "메갈", "메갈리아", "미제", "미친", "병신", "보슬", "보추", "불알", "뻐큐", 
            "싸이코", "싸기지", "새끼", "씹", "아가리", "양놈", "양아치", "엠창", "워마드", 
            "육변기", "좆", "쪽바리", "창녀", "창놈", "최순실", "틀딱", "보장지", "보댕이", 
            "한남", "호구", "후장", "후빨", "ㅄ", "ㅅㅂ", "ㅗ", "야발",
            "섹스", "시1발", "시발", "씨1발", "씨잘", "씨벌", "쎅스", "교미", 
            "성관계", "발가락", 

            "10새", "10새기", "10새리", "10세리", "10쉐이", "10쉑", "10스", "10쌔", "10쌔기", 
            "10쎄", "10알", "10창", "10탱", "18것", "18넘", "18년", "18노", "18놈", "18뇬", 
            "18럼", "18롬" , "18새", "18새끼", "18색", "18세끼", "18세리", "18섹", "18쉑", 
            "18스", "18아", "ㄱㅐ", "ㄲㅏ", "ㄲㅑ", "ㄲㅣ", "ㅅㅂㄹㅁ", "ㅅㅐ", "ㅆㅂㄹㅁ", 
            "ㅆㅍ", "ㅆㅣ", "ㅆ앙", "ㅍㅏ", "凸", "갈보", "갈보년", "같은년", "같은뇬", 
            "개같은", "개구라", "개년", "개놈", "개뇬", "개대중", "개독", "개돼중", "개랄", 
            "개보지", "개뻥", "개뿔", "개새", "개새기", "개새끼", "개새키", "개색기", "개색끼", 
            "개색키", "개색히", "개섀끼", "개세", "개세끼", "개세이", "개소리", "개쑈", "개쇳기", 
            "개수작", "개쉐", "개쉐리", "개쉐이", "개쉑", "개쉽", "개스끼", "개시키", "개십새기", 
            "개십새끼", "개쐑", "개씹", "개아들", "개자슥", "개자지", "개접", "개좆", "개좌식", 
            "개허접", "걔새", "걔수작", "걔시끼", "걔시키", "걔썌", "걸레", "게색기", "게색끼", 
            "광뇬", "구녕", "구라", "구멍", "그년", "그새끼", "냄비", "놈현", "뇬", "눈깔", 
            "뉘미럴", "니귀미", "니기미", "니미", "니미랄", "니미럴", "니미씹", "니아배", 
            "니아베", "니아비", "니어매", "니어메", "니어미", "닝기리", "닝기미", "대가리", 
            "뎡신", "도라이", "돈놈", "돌아이", "돌은놈", "되질래", "뒈져", "뒈져라", "뒈진", 
            "뒈진다", "뒈질", "뒤질래", "등신", "디져라", "디진다", "디질래", "딩시", "따식", 
            "때놈", "또라이", "똘아이", "똘아이", "뙈놈", "뙤놈", "뙨넘", "뙨놈", "뚜쟁", "띠바", 
            "띠발", "띠불", "띠팔", "메친넘", "메친놈", "미췬", "미췬", "미친", "미친넘", "미친년", 
            "미친놈", "미친새끼", "미친스까이", "미틴", "미틴넘", "미틴년", "미틴놈", "바랄년", 
            "병자", "뱅마", "뱅신", "벼엉신", "병쉰", "병신", "부랄", "부럴", "불알", "불할", 
            "붕가", "붙어먹", "뷰웅", "븅", "븅신", "빌어먹", "빙시", "빙신", "빠가", "빠구리", 
            "빠굴", "빠큐", "뻐큐", "뻑큐", "뽁큐", "상넘이", "상놈을", "상놈의", "상놈이", 
            "새갸", "새꺄", "새끼", "새새끼", "새키", "색끼", "생쑈", "세갸", "세꺄", "세끼", 
            "섹스", "쇼하네", "쉐기", "쉐끼", "쉐리", "쉐에기", "쉐키", "쉑", "쉣" , "쉨",
            "쉬발", "쉬밸", "쉬벌", "쉬뻘", "쉬펄", "쉽알", "스패킹", "스팽", "시궁창", "시끼", 
            "시댕", "시뎅", "시랄", "시벌", "시부랄", "시부럴", "시부리", "시불", "시브랄", "시팍", 
            "시팔", "시펄", "신발끈", "심발끈", "심탱", "십8", "십라", "십새", "십새끼", "십세", 
            "십쉐", "십쉐이", "십스키", "십쌔", "십창", "십탱", "싶알", "싸가지", "싹아지", "쌉년", 
            "쌍넘", "쌍년", "쌍놈", "쌍뇬", "쌔끼", "쌕", "쌩쑈", "쌴년", "썅", "썅년", "썅놈", 
            "썡쇼", "써벌", "썩을년", "썩을놈", "쎄꺄", "쎄엑", "쒸벌", "쒸뻘", "쒸팔", "쒸펄", 
            "쓰바", "쓰박", "쓰발", "쓰벌", "쓰팔", "씁새", "씁얼", "씌파", "씨8", "씨끼", "씨댕", 
            "씨뎅", "씨바", "씨바랄", "씨박", "씨발", "씨방", "씨방새", "씨방세", "씨밸", "씨뱅", 
            "씨벌", "씨벨", "씨봉", "씨봉알", "씨부랄", "씨부럴", "씨부렁", "씨부리", "씨불", 
            "씨붕", "씨브랄", "씨빠", "씨빨", "씨뽀랄", "씨앙", "씨파", "씨팍", "씨팔", "씨펄", 
            "씸년", "씸뇬", "씸새끼", "씹같", "씹년", "씹뇬", "씹보지", "씹새", "씹새기", "씹새끼", 
            "씹새리", "씹세", "씹쉐", "씹스키", "씹쌔", "씹이", "씹자지", "씹질", "씹창", "씹탱", "씹퇭", 
            "씹팔", "씹할", "씹헐", "아가리", "아갈", "아갈이", "아갈통", "아구창", "아구통", 
            "아굴", "얌마", "양넘", "양년", "양놈", "엄창", "엠병", "여물통", "염병", "엿같", 
            "옘병", "옘빙", "오입", "왜년", "왜놈", "욤병", "육갑", "은년", "을년", "이년", 
            "이새끼", "이새키", "이스끼", "이스키", "임마", "자슥", "잡것", "잡넘", "잡년", 
            "잡놈", "저년", "저새끼", "접년", "젖밥", "조까", "조까치", "조낸", "조또", "조랭", 
            "조빠", "조쟁이", "조지냐", "조진다", "조찐", "조질래", "존나", "존나게", "존니", 
            "존만", "존만한", "좀물", "좁년", "좆", "좁밥", "좃까", "좃또", "좃만", "좃밥", 
            "좃이", "좃찐", "좆같", "좆까", "좆나", "좆또", "좆만", "좆밥", "좆이", "좆찐", 
            "좇같", "좇이", "좌식", "주글", "주글래", "주데이", "주뎅", "주뎅이", "주둥아리", 
            "주둥이", "주접", "주접떨", "죽고잡", "죽을래", "죽통", "쥐랄", "쥐롤", "쥬디", 
            "지랄", "지럴", "지롤", "지미랄", "짜식", "짜아식", "쪼다", "쫍빱", "찌랄", "창녀", 
            "캐년", "캐놈", "캐스끼", "캐스키", "캐시키", "탱구", "팔럼", "퍽큐", "호로놈",
            "호로새끼", "호로색", "호로쉑", "호로스까이", "호로스키", "후라들", "후래자식", 
            "후레", "후뢰", "씨ㅋ발", "ㅆ1발", "씌발", "띠발", "띄발", "뛰발", "띠ㅋ발", "뉘뮈", 

            "asshole", "bitch", "butt", "cock", "crap", "cunt", "dick", "dumb", 
            "fuck", "gay", "heck", "honky", "kyke", "mot", "mf", "muff", "piss", "nigger", 
            "pussy", "suck", "shit", "tard", "tojo", "THOT", "vagina",
            "whore", "slut", "weeaboo", "your mom", "your mommy", "your mother", 
            "zatch", "zipperhead"
    };
    private static String[] charList = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", 
            "~", "`", "|", "<", ">", "\"", "\'", ", ", ".", "?", "/", "\\", "-", "_", "+", "=", " "
    };
    private static String[] webList = {
            ".gg", ".edu", ".org", ".net", ".mil", ".com", ".gov", ".int", ".museum", 
            ".info", ".coop", ".biz", "aero", "pro", ".mobi", ".travel", ".jobs", ".cat", 
            ".tel", ".asia", 
            ".aaa", ".aarp", ".abarth", ".abb", ".abbott", ".abbive", ".abc", ".able", 
            ".abogado", ".abudhabi", ".ac", ".academy", ".accenture", "..accountants", 
            ".aco", ".active", ".actor", ".ad", ".adac", ".ads", ".adult", ".ae", ".aeg", 
            ".aero", ".aetna", ".af", ".afamilycompany", ".afl", ".africa", ".ag", 
            ".agakhan", ".agency", ".ai", ".aig", ".aigo", ".airbus", ".airforce", 
            ".airtel", ".akdn", ".al", ".alfaromeo", ".alibaba", ".alipay", ".allfinanz", 
            ".allstate", ".ally", ".alsace", ".alstom", ".am", ".americanexpress", 
            ".americanfamily", ".amex", ".amfam", ".amica", ".amsterdam", ".an", 
            ".analytics", ".android", ".anquan", ".anz", ".ao", ".aol", ".apartments", 
            ".app", ".apple", ".aq", ".aquarelle", ".ar", ".arab", ".aramco", ".archi", 
            ".army", ".arpa", ".art", ".arte", ".as", ".asda", ".asia", ".associates",
            ".at", ".athleta", ".attorney", ".au", ".auction", ".audi", ".audible",
            ".audio", ".auspost", ".author", ".auto", ".autos", ".avianca", ".aw",
            ".aws", ".ax", ".axa", ".az", ".azure", ".ba", ".baby", ".baidu",
            ".banamex", ".bananarepublic", ".band", ".bank", ".bar", ".barcelona",
            ".barclaycard", ".barclays", ".barefoot", ".bargains", ".baseball",
            ".basketball", ".bauhaus", ".bayern", ".bb", ".bbc", ".bbt", ".bbva",
            ".bcg", ".bcn", ".bd", ".be", ".beats", ".beauty", ".beer", ".bentley",
            ".berlin", ".best", ".bestbuy", ".bet", ".bf", ".bg", ".bh", ".bharti",
            ".bi", ".bible", ".bid", ".bike", ".bing", ".bingo", ".bio", ".biz",
            ".bj", ".bl", ".black", ".blackfriday", ".blanco", ".blockbuster",
            ".blog", ".bloomberg", ".blue", ".bm", ".bms", ".bmw", ".bn", ".bnl",
            ".bnpparibas", ".bo", ".boats", ".boehringer", ".bofa", ".bom", ".bond",
            ".boo", ".book", ".booking", ".boots", ".bosch", ".bostik", ".boston",
            ".bot", ".boutique", ".box", ".bq", ".br", ".bradesco", ".bridgestone",
            ".broadway", ".broker", ".brother", ".brussels", ".bs", ".bt", ".budapest",
            ".bugatti", ".build", ".builders", ".business", ".buy", ".buzz", ".bv",
            ".bw", ".by", ".bz", ".bzh", ".ca", ".cab", ".cafe", ".cal", ".call",
            ".calvinklein", ".cam", ".camera", ".camp", ".cancerresearch", ".canon",
            ".capetown", ".capital", ".capitalone", ".car", ".caravan", ".cards",
            ".care", ".career", ".careers", ".cars", ".cartier", ".casa", ".case",
            ".caseih", ".cash", ".casino", ".cat", ".catering", ".catholic", ".cba",
            ".cbn", ".cbre", ".cbs", ".cc", ".cd", ".ceb", ".center", ".ceo",
            ".cern", ".cf", ".cfa", ".cfd", ".cg", ".ch", ".chanel", ".channel",
            ".charity", ".chase", ".chat", ".cheap", ".chintai", ".chloe", ".christmas",
            ".chrome", ".chrysler", ".church", ".ci", ".cipriani", ".circle", ".cisco",
            ".citadel", ".citi", ".citic", ".city", ".cityeats", ".ck", ".cl", ".claims",
            ".cleaning", ".click", ".clinic", ".clinique", ".clothing", ".cloud",
            ".club", ".clubmed", ".cm", ".cn", ".co", ".coach", ".codes", ".coffee",
            ".college", ".cologne", ".com", ".comcast", ".commbank", ".community",
            ".company", ".compare", ".computer", ".comsec", ".condos", ".construction",
            ".consulting", ".contact", ".contractors", ".cooking", ".cookingchannel",
            ".cool", ".coop", ".corsica", ".country", ".coupon", ".coupons", ".courses",
            ".cr", ".credit", ".creditcard", ".creditunion", ".cricket", ".crown", ".crs",
            ".cruise", ".cruises", ".csc", ".cu", ".cuisinella", ".cv", ".cw", ".cx", ".cy",
            ".cymru", ".cyou", ".cz", ".dabur", ".dad", ".dance", ".data", ".date", ".dating",
            ".datsun", ".day", ".dclk", ".dds", ".de", ".deal", ".dealer", ".deals", ".degree",
            ".delivery", ".dell", ".deloitte", ".delta", ".democrat", ".dental", ".dentist",
            ".desi", ".design", ".dev", ".dhl", ".diamonds", ".diet", ".digital", ".direct",
            ".directory", ".discount", ".discover", ".dish", ".diy", ".dj", ".dk", ".dm",
            ".dnp", ".do", ".docs", ".doctor", ".dodge", ".dog", ".doha", ".domains",
            ".doosan", ".dot", ".download", ".drive", ".dtv", ".dubai", ".duck", ".dunlop",
            ".duns", ".dupont", ".durban", ".dvag", ".dvr", ".dz", ".earth", ".eat", ".ec",
            ".eco", ".edeka", ".edu", ".education", ".ee", ".eg", ".eh", ".email", ".emerck",
            ".energy", ".engineer", ".engineering", ".enterprises", ".epost", ".epson",
            ".equipment", ".er", ".ericsson", ".erni", ".es", ".esq", ".estate", ".esurance",
            ".et", ".etisalat", ".eu", ".eurovision", ".eus", ".events", ".everbank", ".exchange",
            ".expert", ".exposed", ".express", ".extraspace", ".fage", ".fail", ".fairwinds",
            ".faith", ".family", ".fan", ".fans", ".farm", ".farmers", ".fashion", ".fast",
            ".fedex", ".feedback", ".ferrari", ".ferrero", ".fi", ".fiat", ".fidelity", ".fido",
            ".film", ".final", ".finance", ".financial", ".fire", ".firestone", ".firmdale",
            ".fish", ".fishing", ".fit", ".fitness", ".fj", ".fk", ".flickr", ".flights",
            ".flir", ".florist", ".flowers", ".flsmidth", ".fly", ".fm", ".fo", ".foo", ".food",
            ".foodnetwork", ".football", ".ford", ".forex", ".forsale", ".forum", ".foundation",
            ".fox", ".fr", ".free", ".fresenius", ".frl", ".frogans", ".frontdoor", ".frontier",
            ".ftr", ".fujitsu", ".fujixerox", ".fun", ".fund", ".furniture", ".futbol", ".fyi",
            ".ga", ".gal", ".gallery", ".gallo", ".gallup", ".game", ".games", ".gap", ".garden",
            ".gay", ".gb", ".gbiz", ".gd", ".gdn", ".ge", ".gea", ".gent", ".genting", ".george",
            ".gf", ".gg", ".ggee", ".gh", ".gi", ".gift", ".gifts", ".gives", ".giving", ".gl",
            ".glade", ".glass", ".gle", ".global", ".globo", ".gm", ".gmail", ".gmbh", ".gmo",
            ".gmx", ".gn", ".godaddy", ".gold", ".goldpoint", ".golf", ".goo", ".goodhands",
            ".goodyear", ".goog", ".google", ".gop", ".got", ".gov", ".gp", ".gq", ".gr",
            ".grainger", ".graphics", ".gratis", ".green", ".gripe", ".grocery", ".group", ".gs",
            ".gt", ".gu", ".guardian", ".gucci", ".guge", ".guide", ".guitars", ".guru", ".gw",
            ".gy", ".hair", ".hamburg", ".hangout", ".haus", ".hbo", ".hdfc", ".hdfcbank", ".health",
            ".healthcare", ".help", ".helsinki", ".here", ".hermes", ".hgtv", ".hiphop", ".hisamitsu",
            ".hitachi", ".hiv", ".hk", ".hkt", ".hm", ".hn", ".hockey", ".holdings", ".holiday",
            ".homedepot", ".homegoods", ".homes", ".homesense", ".honda", ".honeywell", ".horse",
            ".hospital", ".host", ".hosting", ".hot", ".hoteles", ".hotels", ".hotmail", ".house",
            ".how", ".hr", ".hsbc", ".ht", ".htc", ".hu", ".hughes", ".hyatt", ".hyundai", ".ibm",
            ".icbc", ".ice", ".icu", ".id", ".ie", ".ieee", ".ifm", ".iinet", ".ikano", ".il",
            ".im", ".imamat", ".imdb", ".immo", ".immobilien", ".in", ".inc", ".industries",
            ".infiniti", ".info", ".ing", ".ink", ".institute", ".insurance", ".insure", ".int",
            ".intel", ".international", ".intuit", ".investments", ".io", ".ipiranga", ".iq",
            ".ir", ".irish", ".is", ".iselect", ".ismaili", ".ist", ".istanbul", ".it", ".itau",
            ".itv", ".iveco", ".iwc", ".jaguar", ".java", ".jcb", ".jcp", ".je", ".jeep", ".jetzt",
            ".jewelry", ".jio", ".jlc", ".jll", ".jm", ".jmp", ".jnj", ".jo", ".jobs", ".joburg",
            ".jot", ".joy", ".jp", ".jpmorgan", ".jprs", ".juegos", ".juniper", ".kaufen", ".kddi",
            ".ke", ".kerryhotels", ".kerrylogistics", ".kerryproperties", ".kfh", ".kg", ".kh",
            ".ki", ".kia", ".kim", ".kinder", ".kindle", ".kitchen", ".kiwi", ".km", ".kn", ".koeln",
            ".komatsu", ".kosher", ".kp", ".kpmg", ".kpn", ".kr", ".krd", ".kred", ".kuokgroup",
            ".kw", ".ky", ".kyoto", ".kz", ".la", ".lacaixa", ".ladbrokes", ".lamborghini",
            ".lamer", ".lancaster", ".lancia", ".lancome", ".land", ".landrover", ".lanxess",
            ".lasalle", ".lat", ".latino", ".latrobe", ".law", ".lawyer", ".lb", ".lc", ".lds",
            ".lease", ".leclerc", ".lefrak", ".legal", ".lego", ".lexus", ".lgbt", ".li", ".liaison",
            ".lidl", ".life", ".lifeinsurance", ".lifestyle", ".lighting", ".like", ".lilly",
            ".limited", ".limo", ".lincoln", ".linde", ".link", ".lipsy", ".live", ".living",
            ".lixil", ".lk", ".llc", ".loan", ".loans", ".locker", ".locus", ".loft", ".lol",
            ".london", ".lotte", ".lotto", ".love", ".lpl", ".lplfinancial", ".lr", ".ls", ".lt",
            ".ltd", ".ltda", ".lu", ".lundbeck", ".lupin", ".luxe", ".luxury", ".lv", ".ly", ".ma",
            ".macys", ".madrid", ".maif", ".maison", ".makeup", ".man", ".management", ".mango",
            ".map", ".market", ".marketing", ".markets", ".marriott", ".marshalls", ".maserati",
            ".mattel", ".mba", ".mc", ".mcd", ".mcdonalds", ".mckinsey", ".md", ".me", ".med",
            ".media", ".meet", ".melbourne", ".meme", ".memorial", ".men", ".menu", ".meo", ".merckmsd",
            ".metlife", ".mf", ".mg", ".mh", ".miami", ".microsoft", ".mil", ".mini", ".mint",
            ".mit", ".mitsubishi", ".mk", ".ml", ".mlb", ".mls", ".mm", ".mma", ".mn", ".mo",
            ".mobi", ".mobile", ".mobily", ".moda", ".moe", ".moi", ".mom", ".monash", ".money",
            ".monster", ".montblanc", ".mopar", ".mormon", ".mortgage", ".moscow", ".moto",
            ".motorcycles", ".mov", ".movie", ".movistar", ".mp", ".mq", ".mr", ".ms", ".msd",
            ".mt", ".mtn", ".mtpc", ".mtr", ".mu", ".museum", ".mutual", ".mutuelle", ".mv",
            ".mw", ".mx", ".my", ".mz", ".na", ".nab", ".nadex", ".nagoya", ".name", ".nationwide",
            ".natura", ".navy", ".nba", ".nc", ".ne", ".nec", ".net", ".netbank", ".netflix",
            ".network", ".neustar", ".new", ".newholland", ".news", ".next", ".nextdirect",
            ".nexus", ".nf", ".nfl", ".ng", ".ngo", ".nhk", ".ni", ".nico", ".nike", ".nikon",
            ".ninja", ".nissan", ".nissay", ".nl", ".no", ".nokia", ".northwesternmutual",
            ".norton", ".now", ".nowruz", ".nowtv", ".np", ".nr", ".nra", ".nrw", ".ntt", ".nu",
            ".nyc", ".nz", ".obi", ".observer", ".off", ".office", ".okinawa", ".olayan",
            ".olayangroup", ".oldnavy", ".ollo", ".om", ".omega", ".one", ".ong", ".onl",
            ".online", ".onyourside", ".ooo", ".open", ".oracle", ".orange", ".org",
            ".organic", ".orientexpress", ".origins", ".osaka", ".otsuka", ".ott", ".ovh",
            ".pa", ".page", ".pamperedchef", ".panasonic", ".panerai", ".paris", ".pars",
            ".partners", ".parts", ".party", ".passagens", ".pay", ".pccw", ".pe", ".pet", ".pf",
            ".pfizer", ".pg", ".ph", ".pharmacy", ".phd", ".philips", ".phone", ".photo",
            ".photography", ".photos", ".physio", ".piaget", ".pics", ".pictet", ".pictures",
            ".pid", ".pin", ".ping", ".pink", ".pioneer", ".pizza", ".pk", ".pl", ".place",
            ".play", ".playstation", ".plumbing", ".plus", ".pm", ".pn", ".pnc", ".pohl", ".poker",
            ".politie", ".porn", ".post", ".pr", ".pramerica", ".praxi", ".press", ".prime", ".pro",
            ".prod", ".productions", ".prof", ".progressive", ".promo", ".properties", ".property",
            ".protection", ".pru", ".prudential", ".ps", ".pt", ".pub", ".pw", ".pwc", ".py",
            ".qa", ".qpon", ".quebec", ".quest", ".qvc", ".racing", ".radio", ".raid", ".re",
            ".read", ".realestate", ".realtor", ".realty", ".recipes", ".red", ".redstone",
            ".redumbrella", ".rehab", ".reise", ".reisen", ".reit", ".reliance", ".ren", ".rent",
            ".rentals", ".repair", ".report", ".republican", ".rest", ".restaurant", ".review",
            ".reviews", ".rexroth", ".rich", ".richardli", ".ricoh", ".rightathome", ".ril",
            ".rio", ".rip", ".rmit", ".ro", ".rocher", ".rocks", ".rodeo", ".rogers", ".room",
            ".rs", ".rsvp", ".ru", ".rugby", ".ruhr", ".run", ".rw", ".rwe", ".ryukyu", ".sa",
            ".saarland", ".safe", ".safety", ".sakura", ".sale", ".salon", ".samsclub", ".samsung",
            ".sandvik", ".sandvikcoromant", ".sanofi", ".sap", ".sapo", ".sarl", ".sas", ".save",
            ".saxo", ".sb", ".sbi", ".sbs", ".sc", ".sca", ".scb", ".schaeffler", ".schmidt",
            ".scholarships", ".school", ".schule", ".schwarz", ".science", ".scjohnson", ".scor",
            ".scot", ".sd", ".se", ".search", ".seat", ".secure", ".security", ".seek", ".select",
            ".sener", ".services", ".ses", ".seven", ".sew", ".sex", ".sexy", ".sfr", ".sg", ".sh",
            ".shangrila", ".sharp", ".shaw", ".shell", ".shia", ".shiksha", ".shoes", ".shop",
            ".shopping", ".shouji", ".show", ".showtime", ".shriram", ".si", ".silk", ".sina",
            ".singles", ".site", ".sj", ".sk", ".ski", ".skin", ".sky", ".skype", ".sl", ".sling",
            ".sm", ".smart", ".smile", ".sn", ".sncf", ".so", ".soccer", ".social", ".softbank",
            ".software", ".sohu", ".solar", ".solutions", ".song", ".sony", ".soy", ".space",
            ".spiegel", ".sport", ".spot", ".spreadbetting", ".sr", ".srl", ".srt", ".ss", ".st",
            ".stada", ".staples", ".star", ".starhub", ".statebank", ".statefarm", ".statoil",
            ".stc", ".stcgroup", ".stockholm", ".storage", ".store", ".stream", ".studio", ".study",
            ".style", ".su", ".sucks", ".supplies", ".supply", ".support", ".surf", ".surgery",
            ".suzuki", ".sv", ".swatch", ".swiftcover", ".swiss", ".sx", ".sy", ".sydney", ".symantec",
            ".systems", ".sz", ".tab", ".taipei", ".talk", ".taobao", ".target", ".tatamotors",
            ".tatar", ".tattoo", ".tax", ".taxi", ".tc", ".tci", ".td", ".tdk", ".team", ".tech",
            ".technology", ".tel", ".telecity", ".telefonica", ".temasek", ".tennis", ".teva", ".tf",
            ".tg", ".th", ".thd", ".theater", ".theatre", ".tiaa", ".tickets", ".tienda", ".tiffany",
            ".tips", ".tires", ".tirol", ".tj", ".tjmaxx", ".tjx", ".tk", ".tkmaxx", ".tl", ".tm",
            ".tmall", ".tn", ".to", ".today", ".tokyo", ".tools", ".top", ".toray", ".toshiba", ".total",
            ".tours", ".town", ".toyota", ".toys", ".tp", ".tr", ".trade", ".trading", ".training",
            ".travel", ".travelchannel", ".travelers", ".travelersinsurance", ".trust", ".trv",
            ".tt", ".tube", ".tui", ".tunes", ".tushu", ".tv", ".tvs", ".tw", ".tz", ".ua", ".ubank",
            ".ubs", ".uconnect", ".ug", ".uk", ".um", ".unicom", ".university", ".uno", ".uol",
            ".ups", ".us", ".uy", ".uz", ".va", ".vacations", ".vana", ".vanguard", ".vc", ".ve",
            ".vegas", ".ventures", ".verisign", ".versicherung", ".vet", ".vg", ".vi", ".viajes",
            ".video", ".vig", ".viking", ".villas", ".vin", ".vip", ".virgin", ".visa", ".vision",
            ".vista", ".vistaprint", ".viva", ".vivo", ".vlaanderen", ".vn", ".vodka", ".volkswagen",
            ".volvo", ".vote", ".voting", ".voto", ".voyage", ".vu", ".vuelos", ".wales", ".walmart",
            ".walter", ".wang", ".wanggou", ".warman", ".watch", ".watches", ".weather", ".weatherchannel",
            ".webcam", ".weber", ".website", ".wed", ".wedding", ".weibo", ".weir", ".wf", ".whoswho",
            ".wien", ".wiki", ".williamhill", ".win", ".windows", ".wine", ".winners", ".wme",
            ".wolterskluwer", ".woodside", ".work", ".works", ".world", ".wow", ".ws", ".wtc",
            ".wtf", ".xbox", ".xerox", ".xfinity", ".xihuan", ".xin", ".セル", ".佛山", ".慈善",
            ".集", ".在", ".한국", ".大汽", ".点看", ".八卦", ".公益", ".公司", ".香格里拉", ".站",
            ".移", ".我", ".москва", ".испытание", ".аз", ".католик", ".онлайн", ".сайт", ".通",
            ".срб", ".бг", ".бел", ".微博", ".테스트", ".淡", ".ファッション", ".орг", ".ストア",
            ".삼성", ".商", ".商店", ".商城", ".дети", ".мкд", ".ею", ".ポイント", ".新", ".工行",
            ".家電", ".中文", ".中信", ".中", ".中國", ".谷歌", ".電訊盈科", ".物", ".測試", ".クラウド",
            ".通販", ".店", ".餐", ".ком", ".укр", ".香港", ".基", ".食品", ".δοκιμ", ".利浦", ".台",
            ".台灣", ".手表", ".手机", ".мон", ".澳門", ".닷컴", ".政府", ".机", ".机", ".健康",
            ".招聘", ".рус", ".рф", ".珠", ".大拿", ".みんな", ".ググル", ".ευ", ".ελ", ".世界",
            ".書籍", ".址", ".닷넷", ".コム", ".天主", ".游", ".vermogensberater", ".vermogensberatung",
            ".企", ".信息", ".嘉里大酒店", ".嘉里", ".新加坡", ".テスト", ".政", ".xperia", ".xxx",
            ".xyz", ".yachts", ".yahoo", ".yamaxun", ".yandex", ".ye", ".yodobashi", ".yoga", ".yokohama",
            ".you", ".youtube", ".yt", ".yun", ".za", ".zappos", ".zara", ".zero", ".zip", ".zippo",
            ".zm", ".zone", ".zuerich", ".zw"
    };
    public static String[] getCharList() {
        return charList;
    }

    public static String[] getList() {
        return List;
    }

    public static String[] getWebList() {
        return webList;
    }
}
