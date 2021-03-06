// Copyright 2013 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dfp.axis.v201311.lineitemservice;

import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.v201311.LineItem;
import com.google.api.ads.dfp.axis.v201311.LineItemServiceInterface;
import com.google.api.ads.dfp.axis.v201311.LineItemType;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.auth.oauth2.Credential;

/**
 * This example updates a standard line item's priority to high. To determine
 * which line items exist, run GetAllLineItems.java.
 *
 * Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 *
 * Tags: LineItemService.getLineItem
 * Tags: LineItemService.updateLineItems
 *
 * @author Adam Rogal
 */
public class UpdateLineItems {

  // Set the ID of the line item to update.
  private static final String LINE_ITEM_ID = "INSERT_LINE_ITEM_ID_HERE";

  public static void runExample(DfpServices dfpServices, DfpSession session, long lineItemId)
      throws Exception {
    // Get the LineItemService.
    LineItemServiceInterface lineItemService =
        dfpServices.get(session, LineItemServiceInterface.class);

    // Get the line item.
    LineItem lineItem = lineItemService.getLineItem(lineItemId);

    // Update the line item's priority to High if possible.
    if (lineItem.getLineItemType() == LineItemType.STANDARD) {
      lineItem.setPriority(6);

      // Update the line item on the server.
      LineItem[] lineItems = lineItemService.updateLineItems(new LineItem[] {lineItem});

      for (LineItem updatedLineItem : lineItems) {
        System.out.printf(
            "Line item with ID \"%d\" and name \"%s\" was updated.\n",
            updatedLineItem.getId(), updatedLineItem.getName());
      }
    } else {
      System.out.println("No line items were updated.");
    }
  }

  public static void main(String[] args) throws Exception {
    // Generate a refreshable OAuth2 credential similar to a ClientLogin token
    // and can be used in place of a service account.
    Credential oAuth2Credential = new OfflineCredentials.Builder()
        .forApi(Api.DFP)
        .fromFile()
        .build()
        .generateCredential();

    // Construct a DfpSession.
    DfpSession session = new DfpSession.Builder()
        .fromFile()
        .withOAuth2Credential(oAuth2Credential)
        .build();

    DfpServices dfpServices = new DfpServices();

    runExample(dfpServices, session, Long.parseLong(LINE_ITEM_ID));
  }
}
