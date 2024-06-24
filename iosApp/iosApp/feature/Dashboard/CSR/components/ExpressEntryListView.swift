//
//  ExpressEntryListView.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI



struct ExpressEntryListView: View {
    let data: [ExpressEntryDraw]
    
    var body: some View {
        VStack {
            // Search and Filter Section
            HStack {
                Text("Sort by:")
                    .customFont(14, weight: .semiBold)
            
                Button(action: {
                     
                }) {
                    HStack {
                        Image(systemName: "line.horizontal.3.decrease.circle")
                            .resizable()
                            .frame(width: 16, height: 16)
                        Text("Filter")
                            .customFont(12, weight: .semiBold)
                    }
                }
                .buttonStyle(PlainButtonStyle())
                Spacer()
                TextField("Search", text: .constant(""))
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 100)
                    .customFont(14, weight: .semiBold)
            }
            .padding(.horizontal)
            .padding(.top)
             
            Grid(alignment : .leading) {
                GridRow {
                    Text("Date")
                    Text("CRS")
                        .padding(.horizontal,16)
                    Text("Type")
                    Text("Min ITA Score")
                }.customFont(12, weight: .bold)
                
                Divider()
                
                ForEach(data) { stat in
                    GridRow(alignment : .center) {
                        Text("\(stat.formattedDate)")
                            .customFont(12)
                        Text("\(stat.crsScore)")
                            .customFont(12)
                            .padding(.horizontal,16)
                        Text(stat.type)
                            .customFont(12)
                            .multilineTextAlignment(.leading)
                        
                        Text("\(stat.itasIssued)")
                            .customFont(12)
                        
                    }.padding(.vertical, 4)
                     Divider()
 
                }
            }.padding()
            
            
        }
        
    }
}
 
#Preview {
    ScrollView{
        ExpressEntryListView(data: ExpressEntryDraw.exampleData)
    }
}
