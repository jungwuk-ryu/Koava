package me.jungwuk.koava.enums;

import me.jungwuk.koava.Koava;
import java.util.HashMap;

@SuppressWarnings("NonAsciiCharacters")
public class RealTypes {
    private static HashMap<Integer, FID> _cache;

    private RealTypes() { }

    public static FID getFid(int id) {
        if (_cache == null) {
            _generateCache();
        }

        return _cache.get(id);
    }

    private static void _generateCache() {
        _cache = new HashMap<>();
        for (FID fid : FID.values()) {
            _cache.put(fid.getId(), fid);
        }
    }

    public enum FID {
        현재가(10),
        전일대비(11),
        등락율(12),
        최우선_매도호가(27),
        최우선_매수호가(28),
        누적거래량(13),
        누적거래대금(14),
        시가(16),
        고가(17),
        저가(18),
        전일대비기호(25),
        전일거래량대비_계약_주(26),
        거래대금증감(29),
        전일거래량대비_비율(30),
        거래회전율(31),
        거래비용(32),
        시가총액_억(311),
        상한가발생시간(567),
        하한가발생시간(568),
        체결시간(20),
        거래량(15),
        체결강도(228),
        장구분(290),
        KO접근도(691),
        전일_동시간_거래량_비율(851),
        호가시간(21),
        매도호가1(41),
        매도호가수량1(61),
        매도호가직전대비1(81),
        매수호가1(51),
        매수호가수량1(71),
        매수호가직전대비1(91),
        매도호가2(42),
        매도호가수량2(62),
        매도호가직전대비2(82),
        매수호가2(52),
        매수호가수량2(72),
        매수호가직전대비2(92),
        매도호가3(43),
        매도호가수량3(63),
        매도호가직전대비3(83),
        매수호가3(53),
        매수호가수량3(73),
        매수호가직전대비3(93),
        매도호가4(44),
        매도호가수량4(64),
        매도호가직전대비4(84),
        매수호가4(54),
        매수호가수량4(74),
        매수호가직전대비4(94),
        매도호가5(45),
        매도호가수량5(65),
        매도호가직전대비5(85),
        매수호가5(55),
        매수호가수량5(75),
        매수호가직전대비5(95),
        매도호가6(46),
        매도호가수량6(66),
        매도호가직전대비6(86),
        매수호가6(56),
        매수호가수량6(76),
        매수호가직전대비6(96),
        매도호가7(47),
        매도호가수량7(67),
        매도호가직전대비7(87),
        매수호가7(57),
        매수호가수량7(77),
        매수호가직전대비7(97),
        매도호가8(48),
        매도호가수량8(68),
        매도호가직전대비8(88),
        매수호가8(58),
        매수호가수량8(78),
        매수호가직전대비8(98),
        매도호가9(49),
        매도호가수량9(69),
        매도호가직전대비9(89),
        매수호가9(59),
        매수호가수량9(79),
        매수호가직전대비9(99),
        매도호가10(50),
        매도호가수량10(70),
        매도호가직전대비10(90),
        매수호가10(60),
        매수호가수량10(80),
        매수호가직전대비10(100),
        매도호가총잔량(121),
        매도호가총잔량직전대비(122),
        매수호가총잔량(125),
        매수호가총잔량직전대비(126),
        예상체결가(23),
        예상체결수량(24),
        순매수잔량(128),
        매수비율(129),
        순매도잔량(138),
        매도비율(139),
        예상체결가전일종가대비(200),
        예상체결가전일종가대비등락율(201),
        예상체결가전일종가대비기호(238),
        예상체결가_예상체결_시간동안에만_유효한_값(291),
        예상체결량(292),
        예상체결가전일대비기호(293),
        예상체결가전일대비(294),
        예상체결가전일대비등락율(295),
        LP매도호가수량1(621),
        LP매수호가수량1(631),
        LP매도호가수량2(622),
        LP매수호가수량2(632),
        LP매도호가수량3(623),
        LP매수호가수량3(633),
        LP매도호가수량4(624),
        LP매수호가수량4(634),
        LP매도호가수량5(625),
        LP매수호가수량5(635),
        LP매도호가수량6(626),
        LP매수호가수량6(636),
        LP매도호가수량7(627),
        LP매수호가수량7(637),
        LP매도호가수량8(628),
        LP매수호가수량8(638),
        LP매도호가수량9(629),
        LP매수호가수량9(639),
        LP매도호가수량10(630),
        LP매수호가수량10(640),
        전일거래량대비예상체결률(299),
        장운영구분(215),
        투자자별ticker(216),
        시간외매도호가총잔량(131),
        시간외매도호가총잔량직전대비(132),
        시간외매수호가총잔량(135),
        시간외매수호가총잔량직전대비(136),
        매도거래원1(141),
        매도거래원수량1(161),
        매도거래원별증감1(166),
        매도거래원코드1(146),
        매도거래원색깔1(271),
        매수거래원1(151),
        매수거래원수량1(171),
        매수거래원별증감1(176),
        매수거래원코드1(156),
        매수거래원색깔1(281),
        매도거래원2(142),
        매도거래원수량2(162),
        매도거래원별증감2(167),
        매도거래원코드2(147),
        매도거래원색깔2(272),
        매수거래원2(152),
        매수거래원수량2(172),
        매수거래원별증감2(177),
        매수거래원코드2(157),
        매수거래원색깔2(282),
        매도거래원3(143),
        매도거래원수량3(163),
        매도거래원별증감3(168),
        매도거래원코드3(148),
        매도거래원색깔3(273),
        매수거래원3(153),
        매수거래원수량3(173),
        매수거래원별증감3(178),
        매수거래원코드3(158),
        매수거래원색깔3(283),
        매도거래원4(144),
        매도거래원수량4(164),
        매도거래원별증감4(169),
        매도거래원코드4(149),
        매도거래원색깔4(274),
        매수거래원4(154),
        매수거래원수량4(174),
        매수거래원별증감4(179),
        매수거래원코드4(159),
        매수거래원색깔4(284),
        매도거래원5(145),
        매도거래원수량5(165),
        매도거래원별증감5(170),
        매도거래원코드5(150),
        매도거래원색깔5(275),
        매수거래원5(155),
        매수거래원수량5(175),
        매수거래원별증감5(180),
        매수거래원코드5(160),
        매수거래원색깔5(285),
        외국계매도추정합(261),
        외국계매도추정합변동(262),
        외국계매수추정합(263),
        외국계매수추정합변동(264),
        외국계순매수추정합(267),
        외국계순매수변동(268),
        거래소구분(337),
        NAV(36),
        NAV전일대비(37),
        NAV등락율(38),
        추적오차율(39),
        ELW기어링비율(667),
        ELW손익분기율(668),
        ELW자본지지점(669),
        NAV_지수괴리율(265),
        NAV_ETF괴리율(266),
        ELW패리티(666),
        ELW프리미엄(1211),
        ELW이론가(670),
        ELW내재변동성(671),
        ELW델타(672),
        ELW감마(673),
        ELW쎄타(674),
        ELW베가(675),
        ELW로(676),
        LP호가내재변동성(706),
        임의연장(297),
        장전임의연장(592),
        장후임의연장(593),
        상한가(305),
        하한가(306),
        기준가(307),
        조기종료ELW발생(689),
        통화단위(594),
        증거금율표시(382),
        종목정보(370),
        Extra_Item_300(300),
        미결제약정(195),
        이론가(182),
        이론베이시스(184),
        시장베이시스(183),
        괴리율(186),
        미결제약정전일대비(181),
        괴리도(185),
        KOSPI200(197),
        시초미결제약정수량(246),
        최고미결제약정수량(247),
        최저미결제약정수량(248),
        미결제증감(196),
        실시간상한가(1365),
        실시간하한가(1366),
        협의대량누적체결수량(1367),
        매도호가건수1(101),
        매수호가건수1(111),
        매도호가건수2(102),
        매수호가건수2(112),
        매도호가건수3(103),
        매수호가건수3(113),
        매도호가건수4(104),
        매수호가건수4(114),
        매도호가건수5(105),
        매수호가건수5(115),
        매도호가총건수(123),
        매수호가총건수(127),
        호가순잔량(137),
        델타(190),
        감마(191),
        세타(193),
        베가(192),
        로(194),
        내재가치(187),
        선물최근월물지수(219),
        시간가치(188),
        내재변동성_IV(189),
        기준가대비시가등락율(391),
        기준가대비고가등락율(392),
        기준가대비저가등락율(393),
        상승종목수(252),
        상한종목수(251),
        보합종목수(253),
        하락종목수(255),
        하한종목수(254),
        거래형성종목수(256),
        거래형성비율(257),
        장시작예상잔여시간(214),
        종목코드_업종코드(9001),
        종목명(302),
        VI발동구분(9068),
        KOSPI_KOSDAQ_전체구분(9008),
        장전구분(9075),
        VI_발동가격(1221),
        매매체결처리시각(1223),
        VI_해제시각(1224),
        VI_적용구분(1225),
        기준가격_정적(1236),
        기준가격_동적(1237),
        괴리율_정적(1238),
        괴리율_동적(1239),
        VI발동가_등락률(1489),
        VI발동횟수(1490),
        발동방향구분(9069),
        Extra_Item_1279(1279),
        계좌번호(9201),
        주문번호(9203),
        관리자사번(9205),
        주문업무분류(912),
        주문상태(913),
        주문수량(900),
        주문가격(901),
        미체결수량(902),
        체결누계금액(903),
        원주문번호(904),
        주문구분(905),
        매매구분(906),
        매도수구분(907),
        주문_체결시간(908),
        체결번호(909),
        체결가(910),
        체결량(911),
        단위체결가(914),
        단위체결량(915),
        당일매매수수료(938),
        당일매매세금(939),
        거부사유(919),
        화면번호(920),
        터미널번호(921),
        신용구분_실시간_체결용(922),
        대출일_실시간_체결용(923),
        보유수량(930),
        매입단가(931),
        총매입가_당일누적(932),
        주문가능수량(933),
        당일순매수량(945),
        매도_매수구분(946),
        당일총매도손익(950),
        Extra_Item_951(951),
        손익율_실현손익(8019),
        파생상품거래단위(397),
        신용구분(917),
        대출일(916),
        신용금액(957),
        신용이자(958),
        만기일(918),
        당일실현손익_유가(990),
        당일실현손익률_유가(991),
        당일실현손익_신용(992),
        당일실현손익률_신용(993),
        담보대출수량(959),
        Extra_Item_924(924),
        매도수량(202),
        매도금액(204),
        매수수량(206),
        매수금액(208),
        순매수수량(210),
        순매수수량증감(211),
        순매수금액(212),
        순매수금액증감(213);

        final int id;

        FID(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        /**
         * <b>비동기 환경에서 사용할 수 없습니다!</b>
         * getCommRealData(realKey, this)의 결과를 반환합니다.
         * onReceiveRealData 콜백 안에서 이 fid에 해당하는 데이터를 추출할 수 있습니다.
         *
         * @return getCommRealData의 결과
         */
        public String get() {
            Koava koava = Koava.getInstance();
            return koava.getCommRealData(koava.getLastRealKey(), id);
        }

        @Override
        public String toString() {
            return "FID ( " + getId() + " )";
        }
    }

    public static class 주식시세 {
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 시가 = FID.시가;
        public static final FID 고가 = FID.고가;
        public static final FID 저가 = FID.저가;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID 전일거래량대비_계약_주 = FID.전일거래량대비_계약_주;
        public static final FID 거래대금증감 = FID.거래대금증감;
        public static final FID 전일거래량대비_비율 = FID.전일거래량대비_비율;
        public static final FID 거래회전율 = FID.거래회전율;
        public static final FID 거래비용 = FID.거래비용;
        public static final FID 시가총액_억 = FID.시가총액_억;
        public static final FID 상한가발생시간 = FID.상한가발생시간;
        public static final FID 하한가발생시간 = FID.하한가발생시간;
    }

    public static class 주식체결 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 거래량 = FID.거래량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 시가 = FID.시가;
        public static final FID 고가 = FID.고가;
        public static final FID 저가 = FID.저가;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID 전일거래량대비_계약_주 = FID.전일거래량대비_계약_주;
        public static final FID 거래대금증감 = FID.거래대금증감;
        public static final FID 전일거래량대비_비율 = FID.전일거래량대비_비율;
        public static final FID 거래회전율 = FID.거래회전율;
        public static final FID 거래비용 = FID.거래비용;
        public static final FID 체결강도 = FID.체결강도;
        public static final FID 시가총액_억 = FID.시가총액_억;
        public static final FID 장구분 = FID.장구분;
        public static final FID KO접근도 = FID.KO접근도;
        public static final FID 상한가발생시간 = FID.상한가발생시간;
        public static final FID 하한가발생시간 = FID.하한가발생시간;
        public static final FID 전일_동시간_거래량_비율 = FID.전일_동시간_거래량_비율;
    }

    public static class 주식우선호가 {
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
    }

    public static class 주식호가잔량 {
        public static final FID 호가시간 = FID.호가시간;
        public static final FID 매도호가1 = FID.매도호가1;
        public static final FID 매도호가수량1 = FID.매도호가수량1;
        public static final FID 매도호가직전대비1 = FID.매도호가직전대비1;
        public static final FID 매수호가1 = FID.매수호가1;
        public static final FID 매수호가수량1 = FID.매수호가수량1;
        public static final FID 매수호가직전대비1 = FID.매수호가직전대비1;
        public static final FID 매도호가2 = FID.매도호가2;
        public static final FID 매도호가수량2 = FID.매도호가수량2;
        public static final FID 매도호가직전대비2 = FID.매도호가직전대비2;
        public static final FID 매수호가2 = FID.매수호가2;
        public static final FID 매수호가수량2 = FID.매수호가수량2;
        public static final FID 매수호가직전대비2 = FID.매수호가직전대비2;
        public static final FID 매도호가3 = FID.매도호가3;
        public static final FID 매도호가수량3 = FID.매도호가수량3;
        public static final FID 매도호가직전대비3 = FID.매도호가직전대비3;
        public static final FID 매수호가3 = FID.매수호가3;
        public static final FID 매수호가수량3 = FID.매수호가수량3;
        public static final FID 매수호가직전대비3 = FID.매수호가직전대비3;
        public static final FID 매도호가4 = FID.매도호가4;
        public static final FID 매도호가수량4 = FID.매도호가수량4;
        public static final FID 매도호가직전대비4 = FID.매도호가직전대비4;
        public static final FID 매수호가4 = FID.매수호가4;
        public static final FID 매수호가수량4 = FID.매수호가수량4;
        public static final FID 매수호가직전대비4 = FID.매수호가직전대비4;
        public static final FID 매도호가5 = FID.매도호가5;
        public static final FID 매도호가수량5 = FID.매도호가수량5;
        public static final FID 매도호가직전대비5 = FID.매도호가직전대비5;
        public static final FID 매수호가5 = FID.매수호가5;
        public static final FID 매수호가수량5 = FID.매수호가수량5;
        public static final FID 매수호가직전대비5 = FID.매수호가직전대비5;
        public static final FID 매도호가6 = FID.매도호가6;
        public static final FID 매도호가수량6 = FID.매도호가수량6;
        public static final FID 매도호가직전대비6 = FID.매도호가직전대비6;
        public static final FID 매수호가6 = FID.매수호가6;
        public static final FID 매수호가수량6 = FID.매수호가수량6;
        public static final FID 매수호가직전대비6 = FID.매수호가직전대비6;
        public static final FID 매도호가7 = FID.매도호가7;
        public static final FID 매도호가수량7 = FID.매도호가수량7;
        public static final FID 매도호가직전대비7 = FID.매도호가직전대비7;
        public static final FID 매수호가7 = FID.매수호가7;
        public static final FID 매수호가수량7 = FID.매수호가수량7;
        public static final FID 매수호가직전대비7 = FID.매수호가직전대비7;
        public static final FID 매도호가8 = FID.매도호가8;
        public static final FID 매도호가수량8 = FID.매도호가수량8;
        public static final FID 매도호가직전대비8 = FID.매도호가직전대비8;
        public static final FID 매수호가8 = FID.매수호가8;
        public static final FID 매수호가수량8 = FID.매수호가수량8;
        public static final FID 매수호가직전대비8 = FID.매수호가직전대비8;
        public static final FID 매도호가9 = FID.매도호가9;
        public static final FID 매도호가수량9 = FID.매도호가수량9;
        public static final FID 매도호가직전대비9 = FID.매도호가직전대비9;
        public static final FID 매수호가9 = FID.매수호가9;
        public static final FID 매수호가수량9 = FID.매수호가수량9;
        public static final FID 매수호가직전대비9 = FID.매수호가직전대비9;
        public static final FID 매도호가10 = FID.매도호가10;
        public static final FID 매도호가수량10 = FID.매도호가수량10;
        public static final FID 매도호가직전대비10 = FID.매도호가직전대비10;
        public static final FID 매수호가10 = FID.매수호가10;
        public static final FID 매수호가수량10 = FID.매수호가수량10;
        public static final FID 매수호가직전대비10 = FID.매수호가직전대비10;
        public static final FID 매도호가총잔량 = FID.매도호가총잔량;
        public static final FID 매도호가총잔량직전대비 = FID.매도호가총잔량직전대비;
        public static final FID 매수호가총잔량 = FID.매수호가총잔량;
        public static final FID 매수호가총잔량직전대비 = FID.매수호가총잔량직전대비;
        public static final FID 예상체결가 = FID.예상체결가;
        public static final FID 예상체결수량 = FID.예상체결수량;
        public static final FID 순매수잔량 = FID.순매수잔량;
        public static final FID 매수비율 = FID.매수비율;
        public static final FID 순매도잔량 = FID.순매도잔량;
        public static final FID 매도비율 = FID.매도비율;
        public static final FID 예상체결가전일종가대비 = FID.예상체결가전일종가대비;
        public static final FID 예상체결가전일종가대비등락율 = FID.예상체결가전일종가대비등락율;
        public static final FID 예상체결가전일종가대비기호 = FID.예상체결가전일종가대비기호;
        public static final FID 예상체결가_예상체결_시간동안에만_유효한_값 = FID.예상체결가_예상체결_시간동안에만_유효한_값;
        public static final FID 예상체결량 = FID.예상체결량;
        public static final FID 예상체결가전일대비기호 = FID.예상체결가전일대비기호;
        public static final FID 예상체결가전일대비 = FID.예상체결가전일대비;
        public static final FID 예상체결가전일대비등락율 = FID.예상체결가전일대비등락율;
        public static final FID LP매도호가수량1 = FID.LP매도호가수량1;
        public static final FID LP매수호가수량1 = FID.LP매수호가수량1;
        public static final FID LP매도호가수량2 = FID.LP매도호가수량2;
        public static final FID LP매수호가수량2 = FID.LP매수호가수량2;
        public static final FID LP매도호가수량3 = FID.LP매도호가수량3;
        public static final FID LP매수호가수량3 = FID.LP매수호가수량3;
        public static final FID LP매도호가수량4 = FID.LP매도호가수량4;
        public static final FID LP매수호가수량4 = FID.LP매수호가수량4;
        public static final FID LP매도호가수량5 = FID.LP매도호가수량5;
        public static final FID LP매수호가수량5 = FID.LP매수호가수량5;
        public static final FID LP매도호가수량6 = FID.LP매도호가수량6;
        public static final FID LP매수호가수량6 = FID.LP매수호가수량6;
        public static final FID LP매도호가수량7 = FID.LP매도호가수량7;
        public static final FID LP매수호가수량7 = FID.LP매수호가수량7;
        public static final FID LP매도호가수량8 = FID.LP매도호가수량8;
        public static final FID LP매수호가수량8 = FID.LP매수호가수량8;
        public static final FID LP매도호가수량9 = FID.LP매도호가수량9;
        public static final FID LP매수호가수량9 = FID.LP매수호가수량9;
        public static final FID LP매도호가수량10 = FID.LP매도호가수량10;
        public static final FID LP매수호가수량10 = FID.LP매수호가수량10;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 전일거래량대비예상체결률 = FID.전일거래량대비예상체결률;
        public static final FID 장운영구분 = FID.장운영구분;
        public static final FID 투자자별ticker = FID.투자자별ticker;
    }

    public static class 주식시간외호가 {
        public static final FID 호가시간 = FID.호가시간;
        public static final FID 시간외매도호가총잔량 = FID.시간외매도호가총잔량;
        public static final FID 시간외매도호가총잔량직전대비 = FID.시간외매도호가총잔량직전대비;
        public static final FID 시간외매수호가총잔량 = FID.시간외매수호가총잔량;
        public static final FID 시간외매수호가총잔량직전대비 = FID.시간외매수호가총잔량직전대비;
    }

    public static class 주식당일거래원 {
        public static final FID 매도거래원1 = FID.매도거래원1;
        public static final FID 매도거래원수량1 = FID.매도거래원수량1;
        public static final FID 매도거래원별증감1 = FID.매도거래원별증감1;
        public static final FID 매도거래원코드1 = FID.매도거래원코드1;
        public static final FID 매도거래원색깔1 = FID.매도거래원색깔1;
        public static final FID 매수거래원1 = FID.매수거래원1;
        public static final FID 매수거래원수량1 = FID.매수거래원수량1;
        public static final FID 매수거래원별증감1 = FID.매수거래원별증감1;
        public static final FID 매수거래원코드1 = FID.매수거래원코드1;
        public static final FID 매수거래원색깔1 = FID.매수거래원색깔1;
        public static final FID 매도거래원2 = FID.매도거래원2;
        public static final FID 매도거래원수량2 = FID.매도거래원수량2;
        public static final FID 매도거래원별증감2 = FID.매도거래원별증감2;
        public static final FID 매도거래원코드2 = FID.매도거래원코드2;
        public static final FID 매도거래원색깔2 = FID.매도거래원색깔2;
        public static final FID 매수거래원2 = FID.매수거래원2;
        public static final FID 매수거래원수량2 = FID.매수거래원수량2;
        public static final FID 매수거래원별증감2 = FID.매수거래원별증감2;
        public static final FID 매수거래원코드2 = FID.매수거래원코드2;
        public static final FID 매수거래원색깔2 = FID.매수거래원색깔2;
        public static final FID 매도거래원3 = FID.매도거래원3;
        public static final FID 매도거래원수량3 = FID.매도거래원수량3;
        public static final FID 매도거래원별증감3 = FID.매도거래원별증감3;
        public static final FID 매도거래원코드3 = FID.매도거래원코드3;
        public static final FID 매도거래원색깔3 = FID.매도거래원색깔3;
        public static final FID 매수거래원3 = FID.매수거래원3;
        public static final FID 매수거래원수량3 = FID.매수거래원수량3;
        public static final FID 매수거래원별증감3 = FID.매수거래원별증감3;
        public static final FID 매수거래원코드3 = FID.매수거래원코드3;
        public static final FID 매수거래원색깔3 = FID.매수거래원색깔3;
        public static final FID 매도거래원4 = FID.매도거래원4;
        public static final FID 매도거래원수량4 = FID.매도거래원수량4;
        public static final FID 매도거래원별증감4 = FID.매도거래원별증감4;
        public static final FID 매도거래원코드4 = FID.매도거래원코드4;
        public static final FID 매도거래원색깔4 = FID.매도거래원색깔4;
        public static final FID 매수거래원4 = FID.매수거래원4;
        public static final FID 매수거래원수량4 = FID.매수거래원수량4;
        public static final FID 매수거래원별증감4 = FID.매수거래원별증감4;
        public static final FID 매수거래원코드4 = FID.매수거래원코드4;
        public static final FID 매수거래원색깔4 = FID.매수거래원색깔4;
        public static final FID 매도거래원5 = FID.매도거래원5;
        public static final FID 매도거래원수량5 = FID.매도거래원수량5;
        public static final FID 매도거래원별증감5 = FID.매도거래원별증감5;
        public static final FID 매도거래원코드5 = FID.매도거래원코드5;
        public static final FID 매도거래원색깔5 = FID.매도거래원색깔5;
        public static final FID 매수거래원5 = FID.매수거래원5;
        public static final FID 매수거래원수량5 = FID.매수거래원수량5;
        public static final FID 매수거래원별증감5 = FID.매수거래원별증감5;
        public static final FID 매수거래원코드5 = FID.매수거래원코드5;
        public static final FID 매수거래원색깔5 = FID.매수거래원색깔5;
        public static final FID 외국계매도추정합 = FID.외국계매도추정합;
        public static final FID 외국계매도추정합변동 = FID.외국계매도추정합변동;
        public static final FID 외국계매수추정합 = FID.외국계매수추정합;
        public static final FID 외국계매수추정합변동 = FID.외국계매수추정합변동;
        public static final FID 외국계순매수추정합 = FID.외국계순매수추정합;
        public static final FID 외국계순매수변동 = FID.외국계순매수변동;
        public static final FID 거래소구분 = FID.거래소구분;
    }

    public static class ETF_NAV {
        public static final FID NAV = FID.NAV;
        public static final FID NAV전일대비 = FID.NAV전일대비;
        public static final FID NAV등락율 = FID.NAV등락율;
        public static final FID 추적오차율 = FID.추적오차율;
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID ELW기어링비율 = FID.ELW기어링비율;
        public static final FID ELW손익분기율 = FID.ELW손익분기율;
        public static final FID ELW자본지지점 = FID.ELW자본지지점;
        public static final FID NAV_지수괴리율 = FID.NAV_지수괴리율;
        public static final FID NAV_ETF괴리율 = FID.NAV_ETF괴리율;
    }

    public static class ELW_지표 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID ELW패리티 = FID.ELW패리티;
        public static final FID ELW프리미엄 = FID.ELW프리미엄;
        public static final FID ELW기어링비율 = FID.ELW기어링비율;
        public static final FID ELW손익분기율 = FID.ELW손익분기율;
        public static final FID ELW자본지지점 = FID.ELW자본지지점;
    }

    public static class ELW_이론가 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID ELW이론가 = FID.ELW이론가;
        public static final FID ELW내재변동성 = FID.ELW내재변동성;
        public static final FID ELW델타 = FID.ELW델타;
        public static final FID ELW감마 = FID.ELW감마;
        public static final FID ELW쎄타 = FID.ELW쎄타;
        public static final FID ELW베가 = FID.ELW베가;
        public static final FID ELW로 = FID.ELW로;
        public static final FID LP호가내재변동성 = FID.LP호가내재변동성;
    }

    public static class 주식예상체결 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 거래량 = FID.거래량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 전일대비기호 = FID.전일대비기호;
    }

    public static class 주식종목정보 {
        public static final FID 임의연장 = FID.임의연장;
        public static final FID 장전임의연장 = FID.장전임의연장;
        public static final FID 장후임의연장 = FID.장후임의연장;
        public static final FID 상한가 = FID.상한가;
        public static final FID 하한가 = FID.하한가;
        public static final FID 기준가 = FID.기준가;
        public static final FID 조기종료ELW발생 = FID.조기종료ELW발생;
        public static final FID 통화단위 = FID.통화단위;
        public static final FID 증거금율표시 = FID.증거금율표시;
        public static final FID 종목정보 = FID.종목정보;
        public static final FID Extra_Item = FID.Extra_Item_300;
    }

    public static class 선물옵션우선호가 {
        public static final FID 현재가 = FID.현재가;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
    }

    public static class 선물시세 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 거래량 = FID.거래량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 시가 = FID.시가;
        public static final FID 고가 = FID.고가;
        public static final FID 저가 = FID.저가;
        public static final FID 미결제약정 = FID.미결제약정;
        public static final FID 이론가 = FID.이론가;
        public static final FID 이론베이시스 = FID.이론베이시스;
        public static final FID 시장베이시스 = FID.시장베이시스;
        public static final FID 괴리율 = FID.괴리율;
        public static final FID 미결제약정전일대비 = FID.미결제약정전일대비;
        public static final FID 괴리도 = FID.괴리도;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID KOSPI200 = FID.KOSPI200;
        public static final FID 전일거래량대비_계약_주 = FID.전일거래량대비_계약_주;
        public static final FID 시초미결제약정수량 = FID.시초미결제약정수량;
        public static final FID 최고미결제약정수량 = FID.최고미결제약정수량;
        public static final FID 최저미결제약정수량 = FID.최저미결제약정수량;
        public static final FID 전일거래량대비_비율 = FID.전일거래량대비_비율;
        public static final FID 미결제증감 = FID.미결제증감;
        public static final FID 실시간상한가 = FID.실시간상한가;
        public static final FID 실시간하한가 = FID.실시간하한가;
        public static final FID 협의대량누적체결수량 = FID.협의대량누적체결수량;
        public static final FID 상한가 = FID.상한가;
        public static final FID 하한가 = FID.하한가;
    }

    public static class 선물호가잔량 {
        public static final FID 호가시간 = FID.호가시간;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 매도호가1 = FID.매도호가1;
        public static final FID 매도호가수량1 = FID.매도호가수량1;
        public static final FID 매도호가직전대비1 = FID.매도호가직전대비1;
        public static final FID 매도호가건수1 = FID.매도호가건수1;
        public static final FID 매수호가1 = FID.매수호가1;
        public static final FID 매수호가수량1 = FID.매수호가수량1;
        public static final FID 매수호가직전대비1 = FID.매수호가직전대비1;
        public static final FID 매수호가건수1 = FID.매수호가건수1;
        public static final FID 매도호가2 = FID.매도호가2;
        public static final FID 매도호가수량2 = FID.매도호가수량2;
        public static final FID 매도호가직전대비2 = FID.매도호가직전대비2;
        public static final FID 매도호가건수2 = FID.매도호가건수2;
        public static final FID 매수호가2 = FID.매수호가2;
        public static final FID 매수호가수량2 = FID.매수호가수량2;
        public static final FID 매수호가직전대비2 = FID.매수호가직전대비2;
        public static final FID 매수호가건수2 = FID.매수호가건수2;
        public static final FID 매도호가3 = FID.매도호가3;
        public static final FID 매도호가수량3 = FID.매도호가수량3;
        public static final FID 매도호가직전대비3 = FID.매도호가직전대비3;
        public static final FID 매도호가건수3 = FID.매도호가건수3;
        public static final FID 매수호가3 = FID.매수호가3;
        public static final FID 매수호가수량3 = FID.매수호가수량3;
        public static final FID 매수호가직전대비3 = FID.매수호가직전대비3;
        public static final FID 매수호가건수3 = FID.매수호가건수3;
        public static final FID 매도호가4 = FID.매도호가4;
        public static final FID 매도호가수량4 = FID.매도호가수량4;
        public static final FID 매도호가직전대비4 = FID.매도호가직전대비4;
        public static final FID 매도호가건수4 = FID.매도호가건수4;
        public static final FID 매수호가4 = FID.매수호가4;
        public static final FID 매수호가수량4 = FID.매수호가수량4;
        public static final FID 매수호가직전대비4 = FID.매수호가직전대비4;
        public static final FID 매수호가건수4 = FID.매수호가건수4;
        public static final FID 매도호가5 = FID.매도호가5;
        public static final FID 매도호가수량5 = FID.매도호가수량5;
        public static final FID 매도호가직전대비5 = FID.매도호가직전대비5;
        public static final FID 매도호가건수5 = FID.매도호가건수5;
        public static final FID 매수호가5 = FID.매수호가5;
        public static final FID 매수호가수량5 = FID.매수호가수량5;
        public static final FID 매수호가직전대비5 = FID.매수호가직전대비5;
        public static final FID 매수호가건수5 = FID.매수호가건수5;
        public static final FID 매도호가총잔량 = FID.매도호가총잔량;
        public static final FID 매도호가총잔량직전대비 = FID.매도호가총잔량직전대비;
        public static final FID 매도호가총건수 = FID.매도호가총건수;
        public static final FID 매수호가총잔량 = FID.매수호가총잔량;
        public static final FID 매수호가총잔량직전대비 = FID.매수호가총잔량직전대비;
        public static final FID 매수호가총건수 = FID.매수호가총건수;
        public static final FID 호가순잔량 = FID.호가순잔량;
        public static final FID 순매수잔량 = FID.순매수잔량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 예상체결가 = FID.예상체결가;
        public static final FID 예상체결가전일종가대비기호 = FID.예상체결가전일종가대비기호;
        public static final FID 예상체결가전일종가대비 = FID.예상체결가전일종가대비;
        public static final FID 예상체결가전일종가대비등락율 = FID.예상체결가전일종가대비등락율;
        public static final FID 예상체결가_예상체결_시간동안에만_유효한_값 = FID.예상체결가_예상체결_시간동안에만_유효한_값;
        public static final FID 예상체결가전일대비기호 = FID.예상체결가전일대비기호;
        public static final FID 예상체결가전일대비 = FID.예상체결가전일대비;
        public static final FID 예상체결가전일대비등락율 = FID.예상체결가전일대비등락율;
        public static final FID 예상체결량 = FID.예상체결량;
    }

    public static class 선물이론가 {
        public static final FID 미결제약정 = FID.미결제약정;
        public static final FID 이론가 = FID.이론가;
        public static final FID 이론베이시스 = FID.이론베이시스;
        public static final FID 시장베이시스 = FID.시장베이시스;
        public static final FID 괴리율 = FID.괴리율;
        public static final FID 미결제약정전일대비 = FID.미결제약정전일대비;
        public static final FID 괴리도 = FID.괴리도;
        public static final FID 시초미결제약정수량 = FID.시초미결제약정수량;
        public static final FID 최고미결제약정수량 = FID.최고미결제약정수량;
        public static final FID 최저미결제약정수량 = FID.최저미결제약정수량;
    }

    public static class 옵션시세 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 거래량 = FID.거래량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 시가 = FID.시가;
        public static final FID 고가 = FID.고가;
        public static final FID 저가 = FID.저가;
        public static final FID 미결제약정 = FID.미결제약정;
        public static final FID 이론가 = FID.이론가;
        public static final FID 괴리율 = FID.괴리율;
        public static final FID 델타 = FID.델타;
        public static final FID 감마 = FID.감마;
        public static final FID 세타 = FID.세타;
        public static final FID 베가 = FID.베가;
        public static final FID 로 = FID.로;
        public static final FID 미결제약정전일대비 = FID.미결제약정전일대비;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID 전일거래량대비_계약_주 = FID.전일거래량대비_계약_주;
        public static final FID 호가순잔량 = FID.호가순잔량;
        public static final FID 내재가치 = FID.내재가치;
        public static final FID KOSPI200 = FID.KOSPI200;
        public static final FID 시초미결제약정수량 = FID.시초미결제약정수량;
        public static final FID 최고미결제약정수량 = FID.최고미결제약정수량;
        public static final FID 최저미결제약정수량 = FID.최저미결제약정수량;
        public static final FID 선물최근월물지수 = FID.선물최근월물지수;
        public static final FID 미결제증감 = FID.미결제증감;
        public static final FID 시간가치 = FID.시간가치;
        public static final FID 내재변동성_IV = FID.내재변동성_IV;
        public static final FID 전일거래량대비_비율 = FID.전일거래량대비_비율;
        public static final FID 기준가대비시가등락율 = FID.기준가대비시가등락율;
        public static final FID 기준가대비고가등락율 = FID.기준가대비고가등락율;
        public static final FID 기준가대비저가등락율 = FID.기준가대비저가등락율;
        public static final FID 실시간상한가 = FID.실시간상한가;
        public static final FID 실시간하한가 = FID.실시간하한가;
        public static final FID 협의대량누적체결수량 = FID.협의대량누적체결수량;
        public static final FID 상한가 = FID.상한가;
        public static final FID 하한가 = FID.하한가;
    }

    public static class 옵션호가잔량 {
        public static final FID 호가시간 = FID.호가시간;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 매도호가1 = FID.매도호가1;
        public static final FID 매도호가수량1 = FID.매도호가수량1;
        public static final FID 매도호가직전대비1 = FID.매도호가직전대비1;
        public static final FID 매도호가건수1 = FID.매도호가건수1;
        public static final FID 매수호가1 = FID.매수호가1;
        public static final FID 매수호가수량1 = FID.매수호가수량1;
        public static final FID 매수호가직전대비1 = FID.매수호가직전대비1;
        public static final FID 매수호가건수1 = FID.매수호가건수1;
        public static final FID 매도호가2 = FID.매도호가2;
        public static final FID 매도호가수량2 = FID.매도호가수량2;
        public static final FID 매도호가직전대비2 = FID.매도호가직전대비2;
        public static final FID 매도호가건수2 = FID.매도호가건수2;
        public static final FID 매수호가2 = FID.매수호가2;
        public static final FID 매수호가수량2 = FID.매수호가수량2;
        public static final FID 매수호가직전대비2 = FID.매수호가직전대비2;
        public static final FID 매수호가건수2 = FID.매수호가건수2;
        public static final FID 매도호가3 = FID.매도호가3;
        public static final FID 매도호가수량3 = FID.매도호가수량3;
        public static final FID 매도호가직전대비3 = FID.매도호가직전대비3;
        public static final FID 매도호가건수3 = FID.매도호가건수3;
        public static final FID 매수호가3 = FID.매수호가3;
        public static final FID 매수호가수량3 = FID.매수호가수량3;
        public static final FID 매수호가직전대비3 = FID.매수호가직전대비3;
        public static final FID 매수호가건수3 = FID.매수호가건수3;
        public static final FID 매도호가4 = FID.매도호가4;
        public static final FID 매도호가수량4 = FID.매도호가수량4;
        public static final FID 매도호가직전대비4 = FID.매도호가직전대비4;
        public static final FID 매도호가건수4 = FID.매도호가건수4;
        public static final FID 매수호가4 = FID.매수호가4;
        public static final FID 매수호가수량4 = FID.매수호가수량4;
        public static final FID 매수호가직전대비4 = FID.매수호가직전대비4;
        public static final FID 매수호가건수4 = FID.매수호가건수4;
        public static final FID 매도호가5 = FID.매도호가5;
        public static final FID 매도호가수량5 = FID.매도호가수량5;
        public static final FID 매도호가직전대비5 = FID.매도호가직전대비5;
        public static final FID 매도호가건수5 = FID.매도호가건수5;
        public static final FID 매수호가5 = FID.매수호가5;
        public static final FID 매수호가수량5 = FID.매수호가수량5;
        public static final FID 매수호가직전대비5 = FID.매수호가직전대비5;
        public static final FID 매수호가건수5 = FID.매수호가건수5;
        public static final FID 매도호가총잔량 = FID.매도호가총잔량;
        public static final FID 매도호가총잔량직전대비 = FID.매도호가총잔량직전대비;
        public static final FID 매도호가총건수 = FID.매도호가총건수;
        public static final FID 매수호가총잔량 = FID.매수호가총잔량;
        public static final FID 매수호가총잔량직전대비 = FID.매수호가총잔량직전대비;
        public static final FID 매수호가총건수 = FID.매수호가총건수;
        public static final FID 호가순잔량 = FID.호가순잔량;
        public static final FID 순매수잔량 = FID.순매수잔량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 예상체결가 = FID.예상체결가;
        public static final FID 예상체결가전일종가대비기호 = FID.예상체결가전일종가대비기호;
        public static final FID 예상체결가전일종가대비 = FID.예상체결가전일종가대비;
        public static final FID 예상체결가전일종가대비등락율 = FID.예상체결가전일종가대비등락율;
        public static final FID 예상체결가_예상체결_시간동안에만_유효한_값 = FID.예상체결가_예상체결_시간동안에만_유효한_값;
        public static final FID 예상체결가전일대비기호 = FID.예상체결가전일대비기호;
        public static final FID 예상체결가전일대비 = FID.예상체결가전일대비;
        public static final FID 예상체결가전일대비등락율 = FID.예상체결가전일대비등락율;
        public static final FID 예상체결량 = FID.예상체결량;
    }

    public static class 옵션이론가 {
        public static final FID 미결제약정 = FID.미결제약정;
        public static final FID 이론가 = FID.이론가;
        public static final FID 괴리율 = FID.괴리율;
        public static final FID 델타 = FID.델타;
        public static final FID 감마 = FID.감마;
        public static final FID 세타 = FID.세타;
        public static final FID 베가 = FID.베가;
        public static final FID 로 = FID.로;
        public static final FID 미결제약정전일대비 = FID.미결제약정전일대비;
        public static final FID 시초미결제약정수량 = FID.시초미결제약정수량;
        public static final FID 최고미결제약정수량 = FID.최고미결제약정수량;
        public static final FID 최저미결제약정수량 = FID.최저미결제약정수량;
        public static final FID 내재가치 = FID.내재가치;
        public static final FID 시간가치 = FID.시간가치;
        public static final FID 내재변동성_IV = FID.내재변동성_IV;
    }

    public static class 업종지수 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 거래량 = FID.거래량;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 시가 = FID.시가;
        public static final FID 고가 = FID.고가;
        public static final FID 저가 = FID.저가;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID 전일거래량대비_계약_주 = FID.전일거래량대비_계약_주;
    }

    public static class 업종등락 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 상승종목수 = FID.상승종목수;
        public static final FID 상한종목수 = FID.상한종목수;
        public static final FID 보합종목수 = FID.보합종목수;
        public static final FID 하락종목수 = FID.하락종목수;
        public static final FID 하한종목수 = FID.하한종목수;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 거래형성종목수 = FID.거래형성종목수;
        public static final FID 거래형성비율 = FID.거래형성비율;
        public static final FID 전일대비기호 = FID.전일대비기호;
    }

    public static class 장시작시간 {
        public static final FID 장운영구분 = FID.장운영구분;
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 장시작예상잔여시간 = FID.장시작예상잔여시간;
    }

    public static class VI발동_해제 {
        public static final FID 종목코드_업종코드 = FID.종목코드_업종코드;
        public static final FID 종목명 = FID.종목명;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 누적거래대금 = FID.누적거래대금;
        public static final FID VI발동구분 = FID.VI발동구분;
        public static final FID KOSPI_KOSDAQ_전체구분 = FID.KOSPI_KOSDAQ_전체구분;
        public static final FID 장전구분 = FID.장전구분;
        public static final FID VI_발동가격 = FID.VI_발동가격;
        public static final FID 매매체결처리시각 = FID.매매체결처리시각;
        public static final FID VI_해제시각 = FID.VI_해제시각;
        public static final FID VI_적용구분 = FID.VI_적용구분;
        public static final FID 기준가격_정적 = FID.기준가격_정적;
        public static final FID 기준가격_동적 = FID.기준가격_동적;
        public static final FID 괴리율_정적 = FID.괴리율_정적;
        public static final FID 괴리율_동적 = FID.괴리율_동적;
        public static final FID VI발동가_등락률 = FID.VI발동가_등락률;
        public static final FID VI발동횟수 = FID.VI발동횟수;
        public static final FID 발동방향구분 = FID.발동방향구분;
        public static final FID Extra_Item = FID.Extra_Item_1279;
    }

    public static class 주문체결 {
        public static final FID 계좌번호 = FID.계좌번호;
        public static final FID 주문번호 = FID.주문번호;
        public static final FID 관리자사번 = FID.관리자사번;
        public static final FID 종목코드_업종코드 = FID.종목코드_업종코드;
        public static final FID 주문업무분류 = FID.주문업무분류;
        public static final FID 주문상태 = FID.주문상태;
        public static final FID 종목명 = FID.종목명;
        public static final FID 주문수량 = FID.주문수량;
        public static final FID 주문가격 = FID.주문가격;
        public static final FID 미체결수량 = FID.미체결수량;
        public static final FID 체결누계금액 = FID.체결누계금액;
        public static final FID 원주문번호 = FID.원주문번호;
        public static final FID 주문구분 = FID.주문구분;
        public static final FID 매매구분 = FID.매매구분;
        public static final FID 매도수구분 = FID.매도수구분;
        public static final FID 주문_체결시간 = FID.주문_체결시간;
        public static final FID 체결번호 = FID.체결번호;
        public static final FID 체결가 = FID.체결가;
        public static final FID 체결량 = FID.체결량;
        public static final FID 현재가 = FID.현재가;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 단위체결가 = FID.단위체결가;
        public static final FID 단위체결량 = FID.단위체결량;
        public static final FID 당일매매수수료 = FID.당일매매수수료;
        public static final FID 당일매매세금 = FID.당일매매세금;
        public static final FID 거부사유 = FID.거부사유;
        public static final FID 화면번호 = FID.화면번호;
        public static final FID 터미널번호 = FID.터미널번호;
        public static final FID 신용구분_실시간_체결용 = FID.신용구분_실시간_체결용;
        public static final FID 대출일_실시간_체결용 = FID.대출일_실시간_체결용;
    }

    public static class 파생잔고 {
        public static final FID 계좌번호 = FID.계좌번호;
        public static final FID 종목코드_업종코드 = FID.종목코드_업종코드;
        public static final FID 종목명 = FID.종목명;
        public static final FID 현재가 = FID.현재가;
        public static final FID 보유수량 = FID.보유수량;
        public static final FID 매입단가 = FID.매입단가;
        public static final FID 총매입가_당일누적 = FID.총매입가_당일누적;
        public static final FID 주문가능수량 = FID.주문가능수량;
        public static final FID 당일순매수량 = FID.당일순매수량;
        public static final FID 매도_매수구분 = FID.매도_매수구분;
        public static final FID 당일총매도손익 = FID.당일총매도손익;
        public static final FID Extra_Item = FID.Extra_Item_951;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 기준가 = FID.기준가;
        public static final FID 손익율_실현손익 = FID.손익율_실현손익;
        public static final FID 파생상품거래단위 = FID.파생상품거래단위;
        public static final FID 상한가 = FID.상한가;
        public static final FID 하한가 = FID.하한가;
    }

    public static class 잔고 {
        public static final FID 계좌번호 = FID.계좌번호;
        public static final FID 종목코드_업종코드 = FID.종목코드_업종코드;
        public static final FID 신용구분 = FID.신용구분;
        public static final FID 대출일 = FID.대출일;
        public static final FID 종목명 = FID.종목명;
        public static final FID 현재가 = FID.현재가;
        public static final FID 보유수량 = FID.보유수량;
        public static final FID 매입단가 = FID.매입단가;
        public static final FID 총매입가_당일누적 = FID.총매입가_당일누적;
        public static final FID 주문가능수량 = FID.주문가능수량;
        public static final FID 당일순매수량 = FID.당일순매수량;
        public static final FID 매도_매수구분 = FID.매도_매수구분;
        public static final FID 당일총매도손익 = FID.당일총매도손익;
        public static final FID Extra_Item_951 = FID.Extra_Item_951;
        public static final FID 최우선_매도호가 = FID.최우선_매도호가;
        public static final FID 최우선_매수호가 = FID.최우선_매수호가;
        public static final FID 기준가 = FID.기준가;
        public static final FID 손익율_실현손익 = FID.손익율_실현손익;
        public static final FID 신용금액 = FID.신용금액;
        public static final FID 신용이자 = FID.신용이자;
        public static final FID 만기일 = FID.만기일;
        public static final FID 당일실현손익_유가 = FID.당일실현손익_유가;
        public static final FID 당일실현손익률_유가 = FID.당일실현손익률_유가;
        public static final FID 당일실현손익_신용 = FID.당일실현손익_신용;
        public static final FID 당일실현손익률_신용 = FID.당일실현손익률_신용;
        public static final FID 담보대출수량 = FID.담보대출수량;
        public static final FID Extra_Item_924 = FID.Extra_Item_924;
    }

    public static class 종목프로그램매매 {
        public static final FID 체결시간 = FID.체결시간;
        public static final FID 현재가 = FID.현재가;
        public static final FID 전일대비기호 = FID.전일대비기호;
        public static final FID 전일대비 = FID.전일대비;
        public static final FID 등락율 = FID.등락율;
        public static final FID 누적거래량 = FID.누적거래량;
        public static final FID 매도수량 = FID.매도수량;
        public static final FID 매도금액 = FID.매도금액;
        public static final FID 매수수량 = FID.매수수량;
        public static final FID 매수금액 = FID.매수금액;
        public static final FID 순매수수량 = FID.순매수수량;
        public static final FID 순매수수량증감 = FID.순매수수량증감;
        public static final FID 순매수금액 = FID.순매수금액;
        public static final FID 순매수금액증감 = FID.순매수금액증감;
        public static final FID 장시작예상잔여시간 = FID.장시작예상잔여시간;
        public static final FID 장운영구분 = FID.장운영구분;
        public static final FID 투자자별ticker = FID.투자자별ticker;
    }
}