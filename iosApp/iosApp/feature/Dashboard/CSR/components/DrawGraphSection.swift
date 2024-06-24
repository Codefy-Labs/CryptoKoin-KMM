//
//  DrawGraphSection.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI 
import Charts

struct ExpressEntryDraw: Identifiable {
    let id = UUID()
    let drawNumber: Int
    let crsScore: Int
    let drawDate: Date
    let itasIssued: Int
    let type: String
    
    var formattedDate: String { drawDate.formattedDate() }
    
    static let exampleData: [ExpressEntryDraw] = [
        ExpressEntryDraw(drawNumber: 298, crsScore: 663, drawDate: dateFormatter.date(from: "June 19, 2024")!, itasIssued: 1499, type: "Provincial nominees only"),
        ExpressEntryDraw(drawNumber: 297, crsScore: 522, drawDate: dateFormatter.date(from: "May 31, 2024")!, itasIssued: 3000, type: "Canadian Experience Class only"),
        ExpressEntryDraw(drawNumber: 296, crsScore: 676, drawDate: dateFormatter.date(from: "May 30, 2024")!, itasIssued: 2985, type: "Provincial nominees only"),
        ExpressEntryDraw(drawNumber: 295, crsScore: 410, drawDate: dateFormatter.date(from: "April 24, 2024")!, itasIssued: 1400, type: "French language proficiency only"),
        ExpressEntryDraw(drawNumber: 294, crsScore: 529, drawDate: dateFormatter.date(from: "April 23, 2024")!, itasIssued: 2095, type: "General"),
        ExpressEntryDraw(drawNumber: 293, crsScore: 491, drawDate: dateFormatter.date(from: "April 11, 2024")!, itasIssued: 4500, type: "STEM occupations only"),
        ExpressEntryDraw(drawNumber: 292, crsScore: 549, drawDate: dateFormatter.date(from: "April 10, 2024")!, itasIssued: 1280, type: "General"),
        ExpressEntryDraw(drawNumber: 291, crsScore: 338, drawDate: dateFormatter.date(from: "March 26, 2024")!, itasIssued: 1500, type: "French language proficiency only"),
        ExpressEntryDraw(drawNumber: 290, crsScore: 524, drawDate: dateFormatter.date(from: "March 25, 2024")!, itasIssued: 1980, type: "General"),
        ExpressEntryDraw(drawNumber: 289, crsScore: 430, drawDate: dateFormatter.date(from: "March 13, 2024")!, itasIssued: 975, type: "Transport occupations only"),
        ExpressEntryDraw(drawNumber: 288, crsScore: 525, drawDate: dateFormatter.date(from: "March 12, 2024")!, itasIssued: 2850, type: "General"),
        ExpressEntryDraw(drawNumber: 287, crsScore: 336, drawDate: dateFormatter.date(from: "February 29, 2024")!, itasIssued: 2500, type: "French language proficiency only"),
        ExpressEntryDraw(drawNumber: 286, crsScore: 534, drawDate: dateFormatter.date(from: "February 28, 2024")!, itasIssued: 1470, type: "General"),
        ExpressEntryDraw(drawNumber: 285, crsScore: 437, drawDate: dateFormatter.date(from: "February 16, 2024")!, itasIssued: 150, type: "Agriculture and Agri-Food occupations only"),
        ExpressEntryDraw(drawNumber: 284, crsScore: 422, drawDate: dateFormatter.date(from: "February 14, 2024")!, itasIssued: 3500, type: "Healthcare occupations only"),
        ExpressEntryDraw(drawNumber: 283, crsScore: 535, drawDate: dateFormatter.date(from: "February 13, 2024")!, itasIssued: 1490, type: "General"),
        ExpressEntryDraw(drawNumber: 282, crsScore: 365, drawDate: dateFormatter.date(from: "February 1, 2024")!, itasIssued: 7000, type: "French language proficiency only"),
        ExpressEntryDraw(drawNumber: 281, crsScore: 541, drawDate: dateFormatter.date(from: "January 31, 2024")!, itasIssued: 730, type: "General"),
        ExpressEntryDraw(drawNumber: 280, crsScore: 543, drawDate: dateFormatter.date(from: "January 23, 2024")!, itasIssued: 1040, type: "General"),
        ExpressEntryDraw(drawNumber: 279, crsScore: 546, drawDate: dateFormatter.date(from: "January 10, 2024")!, itasIssued: 1510, type: "General")
    ]
    
    private static let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM dd, yyyy"
        return formatter
    }()
}

struct ExpressEntryChartView: View {
    let data: [ExpressEntryDraw]
    
    var body: some View {
        VStack( alignment : .leading, spacing: 16) {
            Text("Recent Draws")
                .customFont(18, weight: .medium)
            
            Chart(data) { draw in
                LineMark(
                    x: .value("Draw Date", draw.drawDate),
                    y: .value("CRS Score", draw.crsScore)
                )
            }
            .chartYAxisLabel("CRS Score")
            .chartXAxisLabel("Date of Draw")
            .padding()
            .frame(height: 200)
        }
        .padding()
    }
}



struct ExpressEntryChartView_Previews: PreviewProvider {
    static var previews: some View {
        ExpressEntryChartView(data: ExpressEntryDraw.exampleData)
    }
}
