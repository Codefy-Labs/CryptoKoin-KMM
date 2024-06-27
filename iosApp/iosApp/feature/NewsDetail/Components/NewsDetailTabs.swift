//
//  NewsDetailTabs.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SwiftUI

struct NewsDetailTabs: View {
    @Binding var selectedTab : Int
    let navigateUp : () -> Void
     
    
    let options: [SegmentControlItem] = [
        SegmentControlItem(name: "", iconString: "ic-paper"),
        SegmentControlItem(name: "Discussion", iconString: "ic-message")
    ]
    
    
    var body: some View {
        ZStack {
            HStack{
                BackButton(onClick:  navigateUp)
                Spacer()
            }
            
            SegmentControl(
                selectedIndex: $selectedTab,
                options: options
            )
            
            HStack{
                Spacer()
                Button(action: {}, label: {
                    Image(systemName: "square.and.arrow.up")
                }).buttonStyle(EffectButtonStyle())
                    .foregroundColor(Color.primary)
            }
        }
        .padding()
        .background(Color(uiColor: UIColor.secondarySystemBackground))
        .shadow(radius: 4)
        .background(Color(uiColor: UIColor.secondarySystemBackground))
    }
}
 
struct SegmentControlItem {
    var name: String? = nil
    var iconString: String? = nil
}

private struct SegmentControl: View{
    @Binding var selectedIndex: Int
    let options: [SegmentControlItem]
    
    public var body: some View {
        HStack{
            
            Spacer()
            ZStack(alignment: .center) {
                GeometryReader { geo in
                    RoundedRectangle(cornerRadius: 6.0)
                        .foregroundColor(.accentColor)
                        .cornerRadius(25)
                        .padding(4)
                        .frame(width: selectedIndex == 0 ? 80 : (geo.size.width / CGFloat(options.count))-25)
                        .shadow(color: .black.opacity(0.1), radius: 2, x: 1, y: 1)
                        .offset(x: selectedIndex == 0 ? 62 :  130, y: 0)
                }
                .frame(height: 50)
                HStack(spacing: 36) {
                    HStack(spacing: 6) {
                        if let iconString = options[0].iconString {
                            Image( iconString)
                                .renderingMode(.template)
                                .resizable()
                                .frame(width: 18, height: 18)
                        }
                        if let name = options[0].name {
                            Text(name)
                        }
                    } .  foregroundColor(selectedIndex == 0 ? Color.white : Color.primary)
                        .customFont(  16, weight: .medium)
                        .background(.gray.opacity(0.00001))
                        .onTapGesture {
                            withAnimation(.easeInOut(duration: 0.150)) {
                                selectedIndex = 0
                            }
                        }
                    
                    HStack(spacing: 6) {
                        if let iconString = options[1].iconString {
                            Image( iconString)
                                .renderingMode(.template)
                                .resizable()
                                .frame(width: 18, height: 18)
                        }
                        if let name = options[1].name {
                            Text(name)
                        }
                    }
                    .foregroundColor(selectedIndex == 1 ? Color.white : Color.primary)
                    .customFont(  16, weight: .medium)
                    .background(.gray.opacity(0.00001))
                    .onTapGesture {
                        withAnimation(.easeInOut(duration: 0.150)) {
                            selectedIndex = 1
                        }
                    }
             
                }
                Spacer()
            }
        }
    }
}

private struct NewsTabContentPreview : View {
    
    @State var selectedTab = 0
    
    var body: some View{
        VStack(spacing : 0){
            NewsDetailTabs(selectedTab: $selectedTab, navigateUp: {})
            Spacer()
        }
    }
}


struct NewsTabPreviews: PreviewProvider {
    static var previews: some View {
        
            NewsTabContentPreview()
    }
}
